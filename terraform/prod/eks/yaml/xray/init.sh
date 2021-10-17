eksctl create iamserviceaccount --name xray-daemon --namespace eks-workshop --cluster eks-workshop-cluster --attach-policy-arn arn:aws:iam::aws:policy/AWSXRayDaemonWriteAccess --approve --override-existing-serviceaccounts
kubectl label serviceaccount xray-daemon app=xray-daemon -n eks-workshop
kubectl create -f daemonset.yaml
kubectl describe daemonset xray-daemon -n eks-workshop
sleep 5
kubectl logs -l app=xray-daemon -n eks-workshop