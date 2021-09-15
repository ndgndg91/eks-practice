init:
	aws eks --region ap-northeast-2 update-kubeconfig --name eks-workshop-cluster
	eksctl utils associate-iam-oidc-provider --region=ap-northeast-2 --cluster=eks-workshop-cluster --approve	
create-namespace:
	cd namespace && kubectl apply -f namespace.yaml
deploy-apps: create-namespace
	cd application && kubectl apply -f seller-auth-deployment.yaml
	cd application && kubectl apply -f buyer-auth-deployment.yaml
	cd application && kubectl apply -f product-deployment.yaml
	cd application && kubectl apply -f order-deployment.yaml
destroy-ingress:
	cd ingress && kubectl delete -f rbac-role-alb-ingress-controller.yaml
	cd ingress && kubectl delete -f ingress-controller-deployment.yaml
	cd ingress && kubectl delete -f ingress.yaml