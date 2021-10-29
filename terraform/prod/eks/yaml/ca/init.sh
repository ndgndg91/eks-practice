echo "start create k8s-asg-policy"
aws iam create-policy   \
  --policy-name k8s-asg-policy \
  --policy-document file://./eks-asg-policy.json
echo "end create k8s-asg-policy"

echo "start create iamserviceaccount"
AWS_ACCOUNT=`aws sts get-caller-identity --output text --query 'Account'`
eksctl create iamserviceaccount \
    --name cluster-autoscaler \
    --namespace kube-system \
    --cluster eks-workshop-cluster \
    --attach-policy-arn "arn:aws:iam::$AWS_ACCOOUNT:policy/k8s-asg-policy" \
    --approve \
    --override-existing-serviceaccounts
echo "end create iamserviceaccount"

echo "start describe cluster-autoscaler serviceaccount"
kubectl -n kube-system describe sa cluster-autoscaler
echo "end describe cluster-autoscaler serviceaccount"

echo "start deploy ca"
kubectl apply -f https://www.eksworkshop.com/beginner/080_scaling/deploy_ca.files/cluster-autoscaler-autodiscover.yaml
echo "end deploy ca"

echo "start annotate safe-to-evict=false"
kubectl -n kube-system annotate deployment.apps/cluster-autoscaler cluster-autoscaler.kubernetes.io/safe-to-evict="false" --overwrite
echo "end annotate safe-to-evict=false"

echo "start replace latest image"
# we need to retrieve the latest docker image available for our EKS version
export K8S_VERSION=$(kubectl version --short | grep 'Server Version:' | sed 's/[^0-9.]*\([0-9.]*\).*/\1/' | cut -d. -f1,2)
export AUTOSCALER_VERSION=$(curl -s "https://api.github.com/repos/kubernetes/autoscaler/releases" | grep '"tag_name":' | sed -s 's/.*-\([0-9][0-9\.]*\).*/\1/' | grep -m1 ${K8S_VERSION})

kubectl -n kube-system \
    set image deployment.apps/cluster-autoscaler \
    cluster-autoscaler=us.gcr.io/k8s-artifacts-prod/autoscaling/cluster-autoscaler:v${AUTOSCALER_VERSION}
echo "end replace latest image"

sleep 30
kubectl -n kube-system logs -f deployment/cluster-autoscaler