package info.s1products.server.message;

public class DataType{

	public static final char INT32 = 'i'; //
	public static final char FLOAT32 = 'f'; //
	public static final char LONG64 = 'l'; //
	public static final char STRING = 's'; //
	public static final char BLOB = 'b'; //
	public static final char BIG_ENDIAN64 = 'h';
	public static final char TIME_TAG = 't';
	public static final char DOUBLE64 = 'd'; //
	public static final char SYMBOL = 'S'; //
	public static final char CHARACTER = 'c'; //
	public static final char COLOR = 'r'; //
	public static final char MIDI_MESSAGE = 'm'; //
	public static final char TRUE = 'T'; //
	public static final char FALSE = 'F'; //
	public static final char NIL = 'N'; //
	public static final char INFINITE = 'I'; //
	public static final char ARRAY_START = '[';
	public static final char ARRAY_END = ']';
	public static final char UNDEFINED = '\0';

	public enum DataTypeEnum {
		
		Int32(INT32),
		Float32(FLOAT32),
		Long64(LONG64),
		String(STRING),
		Blob(BLOB),
		BigEndian64(BIG_ENDIAN64),
		Timetag(TIME_TAG),
		Double64(DOUBLE64),
		Symbol(SYMBOL),
		Character(CHARACTER),
		Color(COLOR),
		MidiMessage(MIDI_MESSAGE),
		True(TRUE),
		False(FALSE),
		Nil(NIL),
		Infinite(INFINITE),
		ArrayStart(ARRAY_START),
		ArrayEnd(ARRAY_END),
		Undefined(UNDEFINED);
		
		private char typeTag;
		
		private DataTypeEnum(char tag){
			
			this.typeTag = tag;
		}

		public char getTypeTag(){
			
			return this.typeTag;
		}
		
		public static DataTypeEnum parse(char typeTag){
			
			switch(typeTag){
			
				case INT32:
					return Int32;
					
				case FLOAT32:
					return Float32;
					
				case LONG64:
					return Long64;
					
				case STRING:
					return String;
					
				case BLOB:
					return Blob;
					
				case BIG_ENDIAN64:
					return BigEndian64;
					
				case TIME_TAG:
					return Timetag;
					
				case DOUBLE64:
					return Double64;
					
				case SYMBOL:
					return Symbol;
					
				case CHARACTER:
					return Character;
					
				case COLOR:
					return Color;
					
				case MIDI_MESSAGE:
					return MidiMessage;
					
				case TRUE:
					return True;
					
				case FALSE:
					return False;
					
				case NIL:
					return Nil;
					
				case INFINITE:
					return Infinite;
					
				case ARRAY_START:
					return ArrayStart;
					
				case ARRAY_END:
					return ArrayEnd;
			}
			
			return Undefined;
		}
	}
}
