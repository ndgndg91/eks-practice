echo "start delete ca"
kubectl delete -f https://www.eksworkshop.com/beginner/080_scaling/deploy_ca.files/cluster-autoscaler-autodiscover.yaml
echo "end delete ca"

echo "start delete iamserviceaccount"
eksctl delete iamserviceaccount \
  --name cluster-autoscaler \
  --namespace kube-system \
  --cluster eks-workshop-cluster \
  --wait
echo "end delete iamserviceaccount"

echo "start delete k8s-asg-policy"
AWS_ACCOUNT=`aws sts get-caller-identity --output text --query 'Account'`
aws iam delete-policy \
  --policy-arn arn:aws:iam::$AWS_ACCOUNT:policy/k8s-asg-policy
echo "end delete k8s-asg-policy"