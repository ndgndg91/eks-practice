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
    kubectl config set-context --current --namespace=eks-workshop
</pre>

- create secret
- when creating new rds, change the secret value
<pre>
    kubectl apply -f auth-rds.yaml
    kubectl get secret auth-rds -n eks-workshop --template={{.data.db_username}} | base64 -d 
    kubectl get secret auth-rds -n eks-workshop --template={{.data.db_password}} | base64 -d 
    kubectl get secret auth-rds -n eks-workshop --template={{.data.db_url}} | base64 -d 
</pre>

- deploy seller-auth pods
<pre>
    System Manager 의 Session Manager 를 통해서 RDS 의  eks_workshop database 와 seller table 을 생성해주어야 한다.
</pre>
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
    kubectl apply -f rbac-role-alb-ingress-controller.yaml
    kubectl apply -f ingress-controller-deployment.yaml    
    kubectl logs -n kube-system $(kubectl get po -n kube-system | egrep -o 'aws-load-balancer-controller[a-zA-Z0-9-]+')
    kubectl apply -f ingress.yaml
</pre>

### urls
- https://raw.githubusercontent.com/kubernetes-sigs/aws-alb-ingress-controller/v1.1.8/docs/examples/rbac-role.yaml
- https://raw.githubusercontent.com/kubernetes-sigs/aws-alb-ingress-controller/master/docs/examples/iam-policy.json

- rollout history, status
<pre>
    kubectl rollout history deployment seller-auth -n eks-workshop
    kubectl rollout history deployment buyer-auth -n eks-workshop
    kubectl rollout history deployment product -n eks-workshop
    kubectl rollout history deployment order -n eks-workshop

    kubectl rollout status deployment seller-auth -n eks-workshop
    kubectl rollout status deployment buyer-auth -n eks-workshop
    kubectl rollout status deployment product -n eks-workshop
    kubectl rollout status deployment order -n eks-workshop
</pre>

- describe deployment
<pre>
    kubectl describe deployment seller-auth -n eks-workshop
    kubectl describe deployment buyer-auth -n eks-workshop
    kubectl describe deployment product -n eks-workshop
    kubectl describe deployment order -n eks-workshop
</pre>

- x-ray ( https://www.eksworkshop.com/intermediate/245_x-ray/ )
<pre>
    sh xray/init.sh
</pre>