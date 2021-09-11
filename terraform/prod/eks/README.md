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
    eksctl utils associate-iam-oidc-provider --region=ap-northeast-2 --cluster=eks-workshop-cluster --approve
    wget -O alb-ingress-iam-policy.json https://raw.githubusercontent.com/kubernetes-sigs/aws-alb-ingress-controller/master/docs/examples/iam-policy.json
    aws iam create-policy --policy-name ALBIngressControllerIAMPolicy --policy-document file://alb-ingress-iam-policy.json
    curl -o rbac-role-alb-ingress-controller.yaml https://raw.githubusercontent.com/kubernetes-sigs/aws-alb-ingress-controller/v1.1.8/docs/examples/rbac-role.yaml
    kubectl apply -f rbac-role-alb-ingress-controller.yaml
    sudo yum install jq -y
    AWS_ACCOUNT_ID=$(aws sts get-caller-identity | jq -r '.Account')
    eksctl create iamserviceaccount --cluster=eks-workshop-cluster --name=alb-ingress-controller --namespace=kube-system --attach-policy-arn=arn:aws:iam::$AWS_ACCOUNT_ID:policy/ALBIngressControllerIAMPolicy --approve
    kubectl apply -f ingress-controller-deployment.yaml    
</pre>


- TODO
ingress controller error
leaderelection.go:270] error retrieving resource lock kube-system/ingress-controller-leader-alb: configmaps "ingress-controller-leader-alb" is forbidden: User "system:serviceaccount:kube-system:alb-ingress-controller" cannot get resource "configmaps" in API group "" in the namespace "kube-system"