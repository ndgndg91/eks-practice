init:
	aws eks --region ap-northeast-2 update-kubeconfig --name eks-workshop-cluster
	eksctl utils associate-iam-oidc-provider --region=ap-northeast-2 --cluster=eks-workshop-cluster --approve	
setup-argocd:
	cd argocd && sh init.sh
setup-application:
	cd helm && helm install --debug workshop ./workshop
	cd helm && helm status workshop
	cd helm && helm history workshop
set-ns-context:
	kubectl config set-context --current --namespace=eks-workshop
deploy-kube-ops-view:
	helm install kube-ops-view stable/kube-ops-view --set namespace=eks-workshop --set service.type=LoadBalancer --set rbac.create=True
	sleep 5
	kubectl get svc kube-ops-view | tail -n 1 | awk '{ print "Kube-ops-view URL = http://"$4 }'
	helm ls --all-namespaces
deploy-metric-server:
	cd hpa && kubectl apply -f metric-server.yaml
	kubectl get apiservice v1beta1.metrics.k8s.io -o json | jq '.status'
deploy-ca:
	cd ca && sh init.sh
deploy-xray-daemonset:
	cd xray && sh init.sh
# deploy-ingress:
	# cd ingress && kubectl apply -f rbac-role-alb-ingress-controller.yaml
	# cd ingress && kubectl apply -f ingress-controller-deployment.yaml
	# sleep 10
	# cd ingress && kubectl apply -f ingress.yaml
	
deploy-all: setup-argocd setup-application set-ns-context deploy-kube-ops-view deploy-metric-server deploy-ca deploy-xray-daemonset
	
destroy-argocd:
	cd argocd && sh clean.sh
destroy-kube-ops-view:
	helm uninstall kube-ops-view -n eks-workshop
destroy-ca:
	cd ca && sh clean.sh
destroy-metric-server:
	cd hpa && kubectl delete -f metric-server.yaml
# destroy-ingress:
	# cd ingress && kubectl delete -f ingress.yaml
	# sleep 120
	# cd ingress && kubectl delete -f ingress-controller-deployment.yaml
	# cd ingress && kubectl delete -f rbac-role-alb-ingress-controller.yaml
destroy-xray-daemonset:
	cd xray && sh clean.sh
destroy-apps:
	cd helm && helm uninstall --debug workshop
destroy-all: destroy-argocd destroy-kube-ops-view destroy-ca destroy-metric-server destroy-xray-daemonset destroy-apps