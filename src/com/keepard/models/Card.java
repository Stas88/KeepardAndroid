package com.keepard.models;

import android.net.Uri;

import com.keepard.content_providers.CardsContentProvider;

public class Card {
	
	public Card() {
	}
	
	public static final Uri CONTENT_URI = Uri.parse("content://"
				                + CardsContentProvider.AUTHORITY + "/cards");
	
	public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.jwei512.notes";
	
	public static final String CARD_ID = "_id";
	
	public static final String NAME = "name";
	
	public static final String DESCRIPTION = "description";

	public static final String CODE = "code";
	
	public static final String IMAGE = "image";
	
	public static final String CARD_IMAGE = "card_image";
	
}
