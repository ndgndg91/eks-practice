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


* TODO
- ingress controller
- ingress