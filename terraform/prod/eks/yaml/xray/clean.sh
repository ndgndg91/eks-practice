kubectl delete label serviceaccount xray-daemon
kubectl delete -f daemonset.yaml
eksctl delete iamserviceaccount --name xray-daemon --namespace eks-workshop --cluster eks-workshop-cluster