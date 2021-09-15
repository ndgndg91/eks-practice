set-profile:
	export AWS_PROFILE=donggil
create-network: set-profile
	cd prod/vpc && terraform plan -var-file="prod.tfvars" -out plan.out
	cd prod/vpc && terraform apply -auto-approve plan.out
	cd prod/security-group && terraform plan -var-file="prod.tfvars" -out plan.out
	cd prod/security-group && terraform apply -auto-approve plan.out
create-eks: set-profile
	cd prod/eks && terraform plan -var-file="prod.tfvars" -out plan.out
	cd prod/eks && terraform apply -auto-approve plan.out
create-bastion: set-profile
	cd prod/bastion && terraform plan -var-file="prod.tfvars" -out plan.out
	cd prod/bastion && terraform apply -auto-approve plan.out
create-rds: set-profile
	cd prod/rds && terraform plan -var-file="prod.tfvars" -out plan.out
	cd prod/rds && terraform apply  -auto-approve plan.out
destroy-rds: set-profile
	cd prod/rds && terraform destroy -var-file="prod.tfvars" -auto-approve
destroy-bastion: set-profile
	cd prod/bastion && terraform destroy -var-file="prod.tfvars" -auto-approve
destroy-eks: set-profile
	cd prod/eks && terraform destroy -var-file="prod.tfvars" -auto-approve
destroy-network: set-profile
	cd prod/security-group && terraform destroy -var-file="prod.tfvars" -auto-approve
	cd prod/vpc && terraform destroy -var-file="prod.tfvars" -auto-approve
	