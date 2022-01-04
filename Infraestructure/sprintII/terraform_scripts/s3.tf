resource "aws_s3_bucket" "grupo4-bucket"{
  bucket = "grupo4-bucket"
  acl    = "private"

versioning {
    enabled = true
  }

tags = {
    Name        = "My bucket"
    Environment = "Dev"
  }

}