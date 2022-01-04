module "ec2_cluster" {
source = "terraform-aws-modules/ec2-instance/aws"
version = "~> 2.0"

name = "pi-ec2"
instance_count = 1

ami = "ami-0e0bf4b3a0c0e0adc"
instance_type = "t2.micro"
key_name      = "pi-grupo4"

vpc_security_group_ids = ["sg-0e616850537dbf414"]
subnet_ids = ["subnet-0f29babee4145bd43"]


tags = {
Terraform = "true"
Environment = "dev"
}
}

