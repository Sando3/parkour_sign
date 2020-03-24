package com.mineverse.main;

import java.util.ArrayList;
import java.util.HashMap;
import javax.annotation.Nullable;
import org.bukkit.Bukkit;
import org.bukkit.Sound;

public enum Gsound {
	/*
	 * 
	 * GSound for 1.14 - 1.8 by Gober
	 * 
	 */
	BLOCK_NOTE_BLOCK_BANJO("", "", "", "", "", ""),
	BLOCK_NOTE_BLOCK_BASEDRUM("same", "BLOCK_NOTE_BASEDRUM", "BLOCK_NOTE_BASEDRUM", "BLOCK_NOTE_BASEDRUM", "BLOCK_NOTE_BASEDRUM", "NOTE_SNARE_DRUM"),
	BLOCK_NOTE_BLOCK_BASS("same", "BLOCK_NOTE_BASS", "BLOCK_NOTE_BASS", "BLOCK_NOTE_BASS", "BLOCK_NOTE_BASS", "NOTE_BASS"),
	BLOCK_NOTE_BLOCK_BELL("same", "BLOCK_NOTE_BELL", "", "", "", ""),
	BLOCK_NOTE_BLOCK_BIT("", "", "", "", "", ""),
	BLOCK_NOTE_BLOCK_CHIME("same", "BLOCK_NOTE_CHIME", "", "", "", ""),
	BLOCK_NOTE_BLOCK_COW_BELL("", "", "", "", "", ""),
	BLOCK_NOTE_BLOCK_DIDGERIDOO("", "", "", "", "", ""),
	BLOCK_NOTE_BLOCK_FLUTE("same", "BLOCK_NOTE_FLUTE", "", "", "", "NOTE_BASS_DRUM"),
	BLOCK_NOTE_BLOCK_GUITAR("same", "BLOCK_NOTE_GUITAR", "", "", "", "NOTE_BASS_GUITAR"),
	BLOCK_NOTE_BLOCK_HARP("same", "BLOCK_NOTE_HARP", "BLOCK_NOTE_HARP", "BLOCK_NOTE_HARP", "BLOCK_NOTE_HARP", "NOTE_PIANO"),
	BLOCK_NOTE_BLOCK_HAT("same", "BLOCK_NOTE_HAT", "BLOCK_NOTE_HAT", "BLOCK_NOTE_HAT", "BLOCK_NOTE_HAT", "NOTE_STICKS"),
	BLOCK_NOTE_BLOCK_IRON_XYLOPHONE("", "", "", "", "", ""),
	BLOCK_NOTE_BLOCK_PLING("same", "BLOCK_NOTE_PLING", "BLOCK_NOTE_PLING", "BLOCK_NOTE_PLING", "BLOCK_NOTE_PLING", "NOTE_PLING"),
	BLOCK_NOTE_BLOCK_SNARE("same", "BLOCK_NOTE_SNARE", "BLOCK_NOTE_SNARE", "BLOCK_NOTE_SNARE", "BLOCK_NOTE_SNARE", "NOTE_SNARE"),
	BLOCK_NOTE_BLOCK_XYLOPHONE("same", "BLOCK_NOTE_XYLOPHONE", "", "", "", "");
	
	private ArrayList<String> snds = new ArrayList<String>();
	Gsound(String... sounds) {
		for (String str : sounds) {
			if (str.equals("same")) {
				snds.add(this.toString());
				continue;
			}
			snds.add(str);
			continue;
		}
	}
	public ArrayList<String> getSoundArrays() {
		return this.snds;
	}
	@Nullable
	public static Gsound match(String val) {
		val = val.toUpperCase();
		for (Gsound gs : Gsound.values()) {
			ArrayList<String> snds = gs.getSoundArrays();
			for (String str : snds) {
				if (!str.equals(val)) continue;
				return gs;
			}
		}
		return null;
	}
	@Nullable
	public Sound parseSound() {
		Sound test = sounds.get(this);
		if (test != null) {
			return test;
		}
		switch (serverVersion) {
		case UNKNOWN:
		case V1_8:
		{
			String val = this.getSoundArrays().get(5);
			Sound soun = null;
			try {
				soun = Sound.valueOf(val);
			} catch (IllegalArgumentException e) {
				soun = this.tryAll();
			}
			if (soun == null) {
				return null;
			}
			sounds.put(this,soun);
			return soun;
		}
		case V1_9:
		{
			String val = this.getSoundArrays().get(4);
			Sound soun = null;
			try {
				soun = Sound.valueOf(val);
			} catch (IllegalArgumentException e) {
				soun = this.tryAll();
			}
			if (soun == null) {
				return null;
			}
			sounds.put(this,soun);
			return soun;
		}
		case V1_10:
		{
			String val = this.getSoundArrays().get(3);
			Sound soun = null;
			try {
				soun = Sound.valueOf(val);
			} catch (IllegalArgumentException e) {
				soun = this.tryAll();
			}
			if (soun == null) {
				return null;
			}
			sounds.put(this,soun);
			return soun;
		}
		case V1_11:
		{
			String val = this.getSoundArrays().get(2);
			Sound soun = null;
			try {
				soun = Sound.valueOf(val);
			} catch (IllegalArgumentException e) {
				soun = this.tryAll();
			}
			if (soun == null) {
				return null;
			}
			sounds.put(this,soun);
			return soun;
		}
		case V1_12:
		{
			String val = this.getSoundArrays().get(1);
			Sound soun = null;
			try {
				soun = Sound.valueOf(val);
			} catch (IllegalArgumentException e) {
				soun = this.tryAll();
			}
			if (soun == null) {
				return null;
			}
			sounds.put(this,soun);
			return soun;
		}
		case V1_13:
		{
			String val = this.getSoundArrays().get(0);
			Sound soun = null;
			try {
				soun = Sound.valueOf(val);
			} catch (IllegalArgumentException e) {
				soun = this.tryAll();
			}
			if (soun == null) {
				return null;
			}
			sounds.put(this,soun);
			return soun;
		}
		case V1_14:
		{
			String val = this.toString();
			Sound soun = null;
			try {
				soun = Sound.valueOf(val);
			} catch (IllegalArgumentException e) {
				soun = this.tryAll();
			}
			if (soun == null) {
				return null;
			}
			sounds.put(this,soun);
			return soun;
		}
		}
		return null;
	}
	@Nullable
	private Sound tryAll() {
		String lm = this.toString();
		Sound x = null;
		try {
			x = Sound.valueOf(lm);
		} catch (IllegalArgumentException e) {
			
		}
		if (x != null) {
			return x;
		}
		for (String str : this.getSoundArrays()) {
			Sound s = null;
			try {
				s = Sound.valueOf(str);
			} catch (IllegalArgumentException e) {
				continue;
			}
			return s;
		}
		return null;
	}
	public enum MinecraftVersion {
		UNKNOWN,
		V1_8,
		V1_9,
		V1_10,
		V1_11,
		V1_12,
		V1_13,
		V1_14;

		public static final MinecraftVersion[] VALUES = MinecraftVersion.values();
	}
	/*
	 * 
	 * Minecraft Versions and enum values
	 *	 WEATHER_RAIN("same", "same", "same", "same", "same", ""),
	 *	   V1_14	   V1_13	V1_12   V1_11   V1_10   V1_9  V1_8
	 */
	static {
		String ver = Bukkit.getVersion();
		boolean found = false;
		if (ver.contains("1.14")) {
			serverVersion = MinecraftVersion.V1_14;
			found = true;
		}
		if (!found && ver.contains("1.13")) {
			serverVersion = MinecraftVersion.V1_13;
			found = true;
		}
		if (!found && ver.contains("1.12")) {
			serverVersion = MinecraftVersion.V1_12;
			found = true;
		}
		if (!found && ver.contains("1.11")) {
			serverVersion = MinecraftVersion.V1_11;
			found = true;
		}
		if (!found && ver.contains("1.10")) {
			serverVersion = MinecraftVersion.V1_10;
			found = true;
		}
		if (!found && ver.contains("1.9")) {
			serverVersion = MinecraftVersion.V1_9;
			found = true;
		}
		if (!found && ver.contains("1.8")) {
			serverVersion = MinecraftVersion.V1_8;
			found = true;
		}
		if (!found) {
	 	serverVersion = MinecraftVersion.UNKNOWN;
		}
	}
	public static MinecraftVersion getServerVersion() {
		return serverVersion;
	}
	private static MinecraftVersion serverVersion;
	private static HashMap<Gsound, Sound> sounds = new HashMap<Gsound, Sound>();
	
}