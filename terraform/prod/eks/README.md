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

- deploy admin pods
<pre>
    kubectl apply -f admin-deployment.yml
    kubectl descirbe pod reedev-admin
    kubectl delete deployment reedev-admin
</pre>

- deploy editor pods
<pre>
    kubectl apply -f editor-deployment.yml
    kubectl descirbe pod reedev-editor
    kubectl delete deployment reedev-editor
</pre>

- expose admin by loadbalancer service
<pre>
    kubectl create -f admin-lb.yml
    kubectl get service/reedev-admin-service-loadbalancer |  awk {'print $1" " $2 " " $4 " " $5'} | column -t
    kubectl delete service/reedev-admin-service-loadbalancer
</pre>

- expose editor by loadbalancer serivce
<pre>
    kubectl create -f editor-lb.yml
    kubectl get service/reedev-editor-service-loadbalancer | aws {'print $1" " $2 " " $4 " " $5'} | column -t
    kubectl delete service/reedev-editor-service-loadbalancer
</pre>


* TODO
- ingress controller
- ingress