# eks-demos

1. aws cli 2 설치 ( aws cli 1 의 경우 특정 버전만 지원, aws cli 2 의 경우 특정 버전 이상 충족) 
https://docs.aws.amazon.com/cli/latest/userguide/install-cliv2-mac.html#cliv2-mac-install-gui

2. aws configure - IAM user (access_key, secret_key)

3. install kubectl<br>
https://docs.aws.amazon.com/ko_kr/eks/latest/userguide/install-kubectl.html

4. install eksctl<br>
https://docs.aws.amazon.com/ko_kr/eks/latest/userguide/getting-started-eksctl.html


### eks cluster 생성 예시<br>
`eksctl create cluster --name eksctl-test --nodegroup-name ng-default --node-type t3.micro --nodes 2` <br>
**or**<br>
`eksctl create cluster --config-file=eksctl-create-cluster.yaml`<br>
생성 후, 확인<br>
`kubectl get node`<br>

NAME                                               | STATUS  | ROLES   | AGE    | VERSION
---------------------------------------------------|--------|--------|-------|--------------------
ip-192-168-55-12.ap-northeast-2.compute.internal   | Ready  | <none> | 8m26s | v1.17.11-eks-cfdc40
ip-192-168-64-226.ap-northeast-2.compute.internal  | Ready  | <none> | 8m27s | v1.17.11-eks-cfdc40


### eks node group 생성 예시<br>
`eksctl create nodegroup --config-file=eksctl-create-ng.yaml`<br>
생성 후, 확인<br>
`eksctl get nodegroup --cluster=eksctl-test`<br>

CLUSTER		|NODEGROUP	|CREATED			|MIN SIZE	|MAX SIZE	|DESIRED CAPACITY	|INSTANCE TYPE	|IMAGE ID
----------|-----------|-------------|---------|---------|-----------------|---------------|------
eksctl-test	|ng-default	|2020-10-14T12:32:06Z	|2		|2		|2			|t3.micro	|ami-025592e84db381916
eksctl-test	|ng1-public	|2020-10-14T12:50:49Z	|2		|2		|2			|t3.micro	|ami-025592e84db381916
eksctl-test	|ng2-managed	|2020-10-14T12:50:49Z	|1		|3		|2

### eks cluster 삭제 예시<br>
`eksctl get cluster`<br>

NAME            |REGION
----------------|------
eksctl-test     |ap-northeast-2

`eksctl delete cluster --name=eksctl-test`
