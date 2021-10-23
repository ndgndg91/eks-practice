init:
	aws eks --region ap-northeast-2 update-kubeconfig --name eks-workshop-cluster
	eksctl utils associate-iam-oidc-provider --region=ap-northeast-2 --cluster=eks-workshop-cluster --approve	
create-namespace:
	cd namespace && kubectl apply -f namespace.yaml
	kubectl config set-context --current --namespace=eks-workshop
create-secrets:
	cd application/secret && kubectl apply -f auth-rds.yaml
deploy-xray-daemonset:
	cd xray && sh init.sh
deploy-apps: 
	cd helm && helm install --debug workshop ./workshop
	cd helm && helm status workshop
	cd helm && helm history workshop
deploy-ingress:
	cd ingress && kubectl apply -f rbac-role-alb-ingress-controller.yaml
	cd ingress && kubectl apply -f ingress-controller-deployment.yaml
	sleep 10
	cd ingress && kubectl apply -f ingress.yaml
	
	
destroy-ingress:
	cd ingress && kubectl delete -f ingress.yaml
	sleep 120
	cd ingress && kubectl delete -f ingress-controller-deployment.yaml
	cd ingress && kubectl delete -f rbac-role-alb-ingress-controller.yaml
destroy-xray-daemonset:
	cd xray && sh clean.sh
destroy-apps:
	cd helm && helm uninstall --debug workshop
destroy-secrets:
	cd application/secret && kubectl delete -f auth-rds.yaml
destroy-namespace:
	cd namespace && kubectl delete -f namespace.yaml