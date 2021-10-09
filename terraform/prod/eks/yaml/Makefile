init:
	aws eks --region ap-northeast-2 update-kubeconfig --name eks-workshop-cluster
	eksctl utils associate-iam-oidc-provider --region=ap-northeast-2 --cluster=eks-workshop-cluster --approve	
create-namespace:
	cd namespace && kubectl apply -f namespace.yaml
create-secrets:
	cd application/secret && kubectl apply -f auth-rds.yaml
deploy-xray-daemonset:
	cd xray && sh init.sh
deploy-apps: create-namespace
	cd application/deployment && kubectl apply -f seller-auth-deployment.yaml
	cd application/deployment && kubectl apply -f buyer-auth-deployment.yaml
	cd application/deployment && kubectl apply -f product-deployment.yaml
	cd application/deployment && kubectl apply -f order-deployment.yaml
deploy-ingress:
	cd ingress && kubectl apply -f rbac-role-alb-ingress-controller.yaml
	cd ingress && kubectl apply -f ingress-controller-deployment.yaml
	sleep 5
	cd ingress && kubectl apply -f ingress.yaml
	
	
destroy-ingress:
	cd ingress && kubectl delete -f ingress.yaml
	cd ingress && kubectl delete -f ingress-controller-deployment.yaml
	sleep 30
	cd ingress && kubectl delete -f rbac-role-alb-ingress-controller.yaml
destroy-xray-daemonset:
	cd xray && sh clean.sh
destroy-apps:
	cd application/deployment && kubectl delete -f seller-auth-deployment.yaml
	cd application/deployment && kubectl delete -f buyer-auth-deployment.yaml
	cd application/deployment && kubectl delete -f product-deployment.yaml
	cd application/deployment && kubectl delete -f order-deployment.yaml
destroy-secrets:
	cd application/secret && kubectl delete -f auth-rds.yaml
destroy-namespace:
	cd namespace && kubectl delete -f namespace.yaml