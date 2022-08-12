// Generated from /home/pochodnicky/wrksp/no-git/callie/callie-scala/src/g4/Joint.g4 by ANTLR 4.10.1
package org.callie.gen.joint;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class JointLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.10.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, NAME=15, DIGITS=16, WS=17;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
			"T__9", "T__10", "T__11", "T__12", "T__13", "NAME", "DIGITS", "WS"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'['", "']'", "'|'", "':'", "'{'", "','", "'}'", "'('", "')'", 
			"'+'", "'-'", "'.'", "'e'", "'E'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, "NAME", "DIGITS", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	  float scale = 1f;

	  public void setScale(float x){
	    scale = x;
	  }


	public JointLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Joint.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\u0004\u0000\u0011R\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002\u0001"+
		"\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004"+
		"\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007"+
		"\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b"+
		"\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002"+
		"\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0001\u0000\u0001\u0000\u0001"+
		"\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001"+
		"\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001"+
		"\u0007\u0001\u0007\u0001\b\u0001\b\u0001\t\u0001\t\u0001\n\u0001\n\u0001"+
		"\u000b\u0001\u000b\u0001\f\u0001\f\u0001\r\u0001\r\u0001\u000e\u0001\u000e"+
		"\u0005\u000eB\b\u000e\n\u000e\f\u000eE\t\u000e\u0001\u000f\u0004\u000f"+
		"H\b\u000f\u000b\u000f\f\u000fI\u0001\u0010\u0004\u0010M\b\u0010\u000b"+
		"\u0010\f\u0010N\u0001\u0010\u0001\u0010\u0000\u0000\u0011\u0001\u0001"+
		"\u0003\u0002\u0005\u0003\u0007\u0004\t\u0005\u000b\u0006\r\u0007\u000f"+
		"\b\u0011\t\u0013\n\u0015\u000b\u0017\f\u0019\r\u001b\u000e\u001d\u000f"+
		"\u001f\u0010!\u0011\u0001\u0000\u0003\u0002\u0000AZaz\u0004\u000009AZ"+
		"__az\u0003\u0000\t\n\r\r  T\u0000\u0001\u0001\u0000\u0000\u0000\u0000"+
		"\u0003\u0001\u0000\u0000\u0000\u0000\u0005\u0001\u0000\u0000\u0000\u0000"+
		"\u0007\u0001\u0000\u0000\u0000\u0000\t\u0001\u0000\u0000\u0000\u0000\u000b"+
		"\u0001\u0000\u0000\u0000\u0000\r\u0001\u0000\u0000\u0000\u0000\u000f\u0001"+
		"\u0000\u0000\u0000\u0000\u0011\u0001\u0000\u0000\u0000\u0000\u0013\u0001"+
		"\u0000\u0000\u0000\u0000\u0015\u0001\u0000\u0000\u0000\u0000\u0017\u0001"+
		"\u0000\u0000\u0000\u0000\u0019\u0001\u0000\u0000\u0000\u0000\u001b\u0001"+
		"\u0000\u0000\u0000\u0000\u001d\u0001\u0000\u0000\u0000\u0000\u001f\u0001"+
		"\u0000\u0000\u0000\u0000!\u0001\u0000\u0000\u0000\u0001#\u0001\u0000\u0000"+
		"\u0000\u0003%\u0001\u0000\u0000\u0000\u0005\'\u0001\u0000\u0000\u0000"+
		"\u0007)\u0001\u0000\u0000\u0000\t+\u0001\u0000\u0000\u0000\u000b-\u0001"+
		"\u0000\u0000\u0000\r/\u0001\u0000\u0000\u0000\u000f1\u0001\u0000\u0000"+
		"\u0000\u00113\u0001\u0000\u0000\u0000\u00135\u0001\u0000\u0000\u0000\u0015"+
		"7\u0001\u0000\u0000\u0000\u00179\u0001\u0000\u0000\u0000\u0019;\u0001"+
		"\u0000\u0000\u0000\u001b=\u0001\u0000\u0000\u0000\u001d?\u0001\u0000\u0000"+
		"\u0000\u001fG\u0001\u0000\u0000\u0000!L\u0001\u0000\u0000\u0000#$\u0005"+
		"[\u0000\u0000$\u0002\u0001\u0000\u0000\u0000%&\u0005]\u0000\u0000&\u0004"+
		"\u0001\u0000\u0000\u0000\'(\u0005|\u0000\u0000(\u0006\u0001\u0000\u0000"+
		"\u0000)*\u0005:\u0000\u0000*\b\u0001\u0000\u0000\u0000+,\u0005{\u0000"+
		"\u0000,\n\u0001\u0000\u0000\u0000-.\u0005,\u0000\u0000.\f\u0001\u0000"+
		"\u0000\u0000/0\u0005}\u0000\u00000\u000e\u0001\u0000\u0000\u000012\u0005"+
		"(\u0000\u00002\u0010\u0001\u0000\u0000\u000034\u0005)\u0000\u00004\u0012"+
		"\u0001\u0000\u0000\u000056\u0005+\u0000\u00006\u0014\u0001\u0000\u0000"+
		"\u000078\u0005-\u0000\u00008\u0016\u0001\u0000\u0000\u00009:\u0005.\u0000"+
		"\u0000:\u0018\u0001\u0000\u0000\u0000;<\u0005e\u0000\u0000<\u001a\u0001"+
		"\u0000\u0000\u0000=>\u0005E\u0000\u0000>\u001c\u0001\u0000\u0000\u0000"+
		"?C\u0007\u0000\u0000\u0000@B\u0007\u0001\u0000\u0000A@\u0001\u0000\u0000"+
		"\u0000BE\u0001\u0000\u0000\u0000CA\u0001\u0000\u0000\u0000CD\u0001\u0000"+
		"\u0000\u0000D\u001e\u0001\u0000\u0000\u0000EC\u0001\u0000\u0000\u0000"+
		"FH\u000209\u0000GF\u0001\u0000\u0000\u0000HI\u0001\u0000\u0000\u0000I"+
		"G\u0001\u0000\u0000\u0000IJ\u0001\u0000\u0000\u0000J \u0001\u0000\u0000"+
		"\u0000KM\u0007\u0002\u0000\u0000LK\u0001\u0000\u0000\u0000MN\u0001\u0000"+
		"\u0000\u0000NL\u0001\u0000\u0000\u0000NO\u0001\u0000\u0000\u0000OP\u0001"+
		"\u0000\u0000\u0000PQ\u0006\u0010\u0000\u0000Q\"\u0001\u0000\u0000\u0000"+
		"\u0004\u0000CIN\u0001\u0006\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}