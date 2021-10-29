- install kubectl
https://docs.aws.amazon.com/ko_kr/eks/latest/userguide/install-kubectl.html

- install helm
<pre>
    curl -sSL https://raw.githubusercontent.com/helm/helm/master/scripts/get-helm-3 | bash
    helm version --short
</pre>

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

- helm add stable repo and search and install kube-ops-view
<pre>
    # helm add stable repo
    helm repo add stable https://charts.helm.sh/stable
    # search
    helm search repo stable
    # install
    helm install kube-ops-view \
    stable/kube-ops-view \
    --set service.type=LoadBalancer \
    --set rbac.create=True
    # svc url
     kubectl get svc kube-ops-view | tail -n 1 | awk '{ print "Kube-ops-view URL = http://"$4 }'
</pre>

- helm chart
<pre>
    cd terraform/prod/eks/yaml
    helm create workshop
    cd workshop

    # delete boilerplate files
    rm values.yaml
    rm -rf templates/
    rm Chart.yaml

    # create Chart.yaml file
    cat <<EoF > Chart.yaml
    apiVersion: v2
    name: eksdemo
    description: A Helm chart for EKS Workshop Microservices application
    version: 0.1.0
    appVersion: 1.0
    EoF
    
    # create subfolders for each template type
    mkdir -p templates/deployment
    cp terraform/prod/eks/yaml/application/deployment/product-deployment.yaml terraform/prod/eks/yaml/helm/workshop/templates/deployment/product-deployment.yaml 
    cp terraform/prod/eks/yaml/application/deployment/order-deployment.yaml terraform/prod/eks/yaml/helm/workshop/templates/deployment/order-deployment.yaml 
    cp terraform/prod/eks/yaml/application/deployment/seller-auth-deployment.yaml terraform/prod/eks/yaml/helm/workshop/templates/deployment/seller-auth-deployment.yaml                                                                                                                                                                       
    cp terraform/prod/eks/yaml/application/deployment/buyer-auth-deployment.yaml terraform/prod/eks/yaml/helm/workshop/templates/deployment/buyer-auth-deployment.yaml
    
    
    
    # replace hard-coded values with template directives
    replicas: {{ .Values.replicas }}
    - image: {{ .Values.buyer.image }}:{{ .Values.version }} in terraform/prod/eks/yaml/helm/workshop/templates/deployment/buyer-auth-deployment.yaml
    - image: {{ .Values.seller.image }}:{{ .Values.version }} in terraform/prod/eks/yaml/helm/workshop/templates/deployment/seller-auth-deployment.yaml
    - image: {{ .Values.product.image }}:{{ .Values.version }} in terraform/prod/eks/yaml/helm/workshop/templates/deployment/product-deployment.yaml
    - image: {{ .Values.order.image }}:{{ .Values.version }} in terraform/prod/eks/yaml/helm/workshop/templates/deployment/order-deployment.yaml
    
    # create values.yaml
    cat <<EoF > ~/environment/eksdemo/values.yaml
    # Default values for workshop.
    # This is a YAML-formatted file.
    # Declare variables to be passed into your templates.

    # Release-wide Values
    replicas: 1
    version: 'v1.1.1'

    # Service Specific Values
    buyer:
        image: 495523830808.dkr.ecr.ap-northeast-2.amazonaws.com/eks-workshop-buyer-auth
    seller:
        image: 495523830808.dkr.ecr.ap-northeast-2.amazonaws.com/eks-workshop-seller-auth
    order:
        image: 495523830808.dkr.ecr.ap-northeast-2.amazonaws.com/eks-workshop-order
    product:
        image: 495523830808.dkr.ecr.ap-northeast-2.amazonaws.com/eks-workshop-product
    EoF
    
    # test template with dry-run option
    helm install --debug --dry-run workshop ./workshop
    
    # apply template to eks cluster
    helm install --debug workshop ./workshop
    
    helm status workshop
    helm history workshop
    
    # Rolling update - after modify values.yaml
    helm upgrade workshop ./workshop
    
    # clean
    helm uninstall workshop
</pre>

- CA(Cluster Autoscaling), HPA(Horizaontal Pod Autoscaling) 
<pre>
    # deploy the metric server
    kubectl apply -f metric-server.yaml
    # current ASG
    aws autoscaling \
    describe-auto-scaling-groups \
    --query "AutoScalingGroups[? Tags[? (Key=='eks:cluster-name') && Value=='eks-workshop-cluster']].[AutoScalingGroupName, MinSize, MaxSize,DesiredCapacity]" \
    --output table
</pre>

- create namespace
<pre>
    kubectl apply -f namespace.yaml
    kubectl get namespace
    kubectl config set-context --current --namespace=eks-workshop
    kubectl get resourcequota
    # 모든 컨테이너에는 메모리 요청량(request), 메모리 상한(limit), CPU 요청량 및 CPU 상한이 있어야 한다.
    # 모든 컨테이너에 대한 총 메모리 요청량은 1GiB를 초과하지 않아야 한다.
    # 모든 컨테이너에 대한 총 메모리 상한은 2GiB를 초과하지 않아야 한다.
    # 모든 컨테이너에 대한 총 CPU 요청량은 1 cpu를 초과해서는 안된다.
    # 모든 컨테이너에 대한 총 CPU 상한은 2 cpu를 초과해서는 안된다.
</pre>

- create secret
- when creating new rds, change the secret value
<pre>
    kubectl apply -f auth-rds.yaml
    kubectl get secret auth-rds -o jsonpath='{.data}'
    echo '{encoded-string}' | base64 --decode
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