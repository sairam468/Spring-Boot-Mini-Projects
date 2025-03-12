package com.sit.binding;

import lombok.Data;

@Data
public class UnlockForm {

	private String tempPass;
	private String email;
	private String newPass;
	private String conPass;
}
