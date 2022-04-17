resource "aws_security_group" "main" {
  name        = "${var.name} security_group"
  description = "Security group encompassing all ${var.name} components"
  vpc_id      = aws_vpc.main.id

  depends_on = [aws_iam_role.iam_role_for_lambda]
}

resource "aws_security_group_rule" "egress-all-manage" {
  security_group_id = aws_security_group.main.id
  type              = "egress"
  from_port         = 1
  to_port           = 65535
  protocol          = -1
  cidr_blocks       = ["0.0.0.0/0"]
}

resource "aws_security_group_rule" "ingress-443" {
  security_group_id = "${aws_security_group.main.id}"
  type              = "ingress"
  from_port         = 443
  to_port           = 443
  protocol          = "TCP"
  cidr_blocks       = ["0.0.0.0/0"]
}