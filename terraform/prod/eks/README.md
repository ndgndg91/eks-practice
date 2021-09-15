- install kubectl
https://docs.aws.amazon.com/ko_kr/eks/latest/userguide/install-kubectl.html

- update kubeconfig for using kubectl
https://docs.aws.amazon.com/ko_kr/eks/latest/userguide/create-kubeconfig.html
<pre>
    $ rm ~/.kube/config 
    $ aws eks --region {region} update-kubeconfig --name {cluster-name}
</pre>

- check eks cluster
<pre>
    kubectl get svc
    kubectl get node
    kubectl get pod
    kubectl get namespace
</pre>

- create namespace
<pre>
    kubectl apply -f namespace.yaml
    kubectl get namespace
</pre>

- deploy seller-auth pods
<pre>
    kubectl apply -f seller-auth-deployment.yaml
    kubectl get pod -n eks-workshop
    kubectl delete -f seller-auth-deployment.yaml
</pre>

- deploy buyer-auth pods
<pre>
    kubectl apply -f buyer-auth-deployment.yaml
    kubectl get pod -n eks-workshop
    kubectl delete -f byuer-auth-deployment.yaml
</pre>

- deploy product pods
<pre>
    kubectl apply -f product-deployment.yaml
    kubectl get pod -n eks-workshop
    kubectl delete -f product-deployment.yaml
</pre>

- deploy order pods
<pre>
    kubectl apply -f order-deployment.yaml
    kubectl get pod -n eks-workshop
    kubectl delete -f order-deployment.yaml
</pre>


- ingress controller
<pre>
    ingress-controller-deployment.yaml 을 통해서 alb-ingress-controller 를 생성하기 전에 node group 이 속한 vpc id 로 변경해주어야 한다.
    ingress.yaml 을 통해서 ingress 를 생성하기 전에 각각 NodePort Service 에 매핑된 Port Number 로 변경해주어야 한다.
</pre>
<pre>
    eksctl utils associate-iam-oidc-provider --region=ap-northeast-2 --cluster=eks-workshop-cluster --approve
    kubectl apply -f rbac-role-alb-ingress-controller.yaml
    kubectl apply -f ingress-controller-deployment.yaml    
    kubectl logs -n kube-system $(kubectl get po -n kube-system | egrep -o 'aws-load-balancer-controller[a-zA-Z0-9-]+')
    kubectl apply -f ingress.yaml
</pre>


### urls
- https://raw.githubusercontent.com/kubernetes-sigs/aws-alb-ingress-controller/v1.1.8/docs/examples/rbac-role.yaml
- https://raw.githubusercontent.com/kubernetes-sigs/aws-alb-ingress-controller/master/docs/examples/iam-policy.json