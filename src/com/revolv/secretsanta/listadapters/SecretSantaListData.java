package com.revolv.secretsanta.listadapters;

public class SecretSantaListData {
	private final String santa;
	private final String giftee;
	
	public SecretSantaListData(String santa, String giftee) {
		this.santa = santa;
		this.giftee = giftee;
	}

	public String getSanta() {
		return santa;
	}

	public String getGiftee() {
		return giftee;
	}
	
}
