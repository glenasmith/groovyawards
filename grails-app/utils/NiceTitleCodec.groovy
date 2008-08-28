// strip all non word chars, convert to lowercase...
class NiceTitleCodec {
	static encode = { str ->
		return str.toString().replaceAll("\\W", "-").toLowerCase()
	}

}