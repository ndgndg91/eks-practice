eksctl utils associate-iam-oidc-provider --region=ap-northeast-2 --cluster=eks-workshop-cluster --approve
eksctl create iamserviceaccount --name xray-daemon --namespace eks-workshop --cluster eks-workshop-cluster --attach-policy-arn arn:aws:iam::aws:policy/AWSXRayDaemonWriteAccess --approve --override-existing-serviceaccounts
kubectl label serviceaccount xray-daemon app=xray-daemon
kubectl create -f daemonset.yaml
kubectl describe daemonset xray-daemon
kubectl logs -l app=xray-daemon