apply:
	export AWWS_PROFILE=k8s-user
	cd prod/vpc && terraform plan -var-file="prod.tfvars" -out plan.out
	cd prod/vpc && terraform apply -auto-approve plan.out
	cd prod/security-group && terraform plan -var-file="prod.tfvars" -out plan.out
	cd prod/security-group && terraform apply -auto-approve plan.out
	cd prod/eks && terraform plan -var-file="prod.tfvars" -out plan.out
	cd prod/eks && terraform apply -auto-approve plan.out
	# cd prod/bastion && terraform plan -var-file="prod.tfvars" -out plan.out
	# cd prod/bastion && terraform apply -auto-approve plan.out
	# cd prod/rds && terraform plan -var-file="prod.tfvars" -out plan.out
	# cd prod/rds && terraform apply  -auto-approve plan.out
destroy:
	export AWWS_PROFILE=k8s-user
	# cd prod/rds && terraform destroy -var-file="prod.tfvars" -auto-approve
	# cd prod/bastion && terraform destroy -var-file="prod.tfvars" -auto-approve
	cd prod/eks && terraform destroy -var-file="prod.tfvars" -auto-approve
	cd prod/security-group && terraform destroy -var-file="prod.tfvars" -auto-approve
	cd prod/vpc && terraform destroy -var-file="prod.tfvars" -auto-approve
	