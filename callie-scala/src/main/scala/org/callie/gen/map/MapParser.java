// Generated from /home/pochodnicky/wrksp/no-git/callie/callie-scala/src/g4/Map.g4 by ANTLR 4.9.1
package org.callie.gen.map;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MapParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.9.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, DIGITS=13, WS=14;
	public static final int
		RULE_map = 0, RULE_int3 = 1, RULE_float3 = 2, RULE_floatNum = 3, RULE_intNum = 4;
	private static String[] makeRuleNames() {
		return new String[] {
			"map", "int3", "float3", "floatNum", "intNum"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'['", "','", "']'", "'{'", "'}'", "'('", "')'", "'+'", "'-'", 
			"'.'", "'e'", "'E'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, "DIGITS", "WS"
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

	@Override
	public String getGrammarFileName() { return "Map.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public MapParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class MapContext extends ParserRuleContext {
		public org.callie.gen.Float3Int3 result;
		public Float3Context f;
		public Float3Context fi;
		public Int3Context i;
		public Int3Context ii;
		public List<Float3Context> float3() {
			return getRuleContexts(Float3Context.class);
		}
		public Float3Context float3(int i) {
			return getRuleContext(Float3Context.class,i);
		}
		public List<Int3Context> int3() {
			return getRuleContexts(Int3Context.class);
		}
		public Int3Context int3(int i) {
			return getRuleContext(Int3Context.class,i);
		}
		public MapContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_map; }
	}

	public final MapContext map() throws RecognitionException {
		MapContext _localctx = new MapContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_map);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{

			        java.util.ArrayList<org.callie.math.Vector3> lf = new java.util.ArrayList<>();
			        java.util.ArrayList<org.callie.gen.Int3> li = new java.util.ArrayList<>();
			     
			setState(11);
			match(T__0);
			setState(12);
			((MapContext)_localctx).f = float3();
			 lf.add(((MapContext)_localctx).f.r); 
			setState(20);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__1) {
				{
				{
				setState(14);
				match(T__1);
				setState(15);
				((MapContext)_localctx).fi = float3();
				 lf.add(((MapContext)_localctx).fi.r); 
				}
				}
				setState(22);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(23);
			match(T__2);
			setState(24);
			match(T__3);
			setState(25);
			((MapContext)_localctx).i = int3();
			 li.add(((MapContext)_localctx).i.r); 
			setState(33);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__1) {
				{
				{
				setState(27);
				match(T__1);
				setState(28);
				((MapContext)_localctx).ii = int3();
				 li.add(((MapContext)_localctx).ii.r); 
				}
				}
				setState(35);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(36);
			match(T__4);
			 ((MapContext)_localctx).result =  new org.callie.gen.Float3Int3(lf.toArray(new org.callie.math.Vector3[0]), li.toArray(new org.callie.gen.Int3[0])); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Int3Context extends ParserRuleContext {
		public org.callie.gen.Int3 r;
		public IntNumContext i;
		public IntNumContext j;
		public IntNumContext k;
		public List<IntNumContext> intNum() {
			return getRuleContexts(IntNumContext.class);
		}
		public IntNumContext intNum(int i) {
			return getRuleContext(IntNumContext.class,i);
		}
		public Int3Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_int3; }
	}

	public final Int3Context int3() throws RecognitionException {
		Int3Context _localctx = new Int3Context(_ctx, getState());
		enterRule(_localctx, 2, RULE_int3);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(39);
			match(T__5);
			setState(40);
			((Int3Context)_localctx).i = intNum();
			setState(41);
			match(T__1);
			setState(42);
			((Int3Context)_localctx).j = intNum();
			setState(43);
			match(T__1);
			setState(44);
			((Int3Context)_localctx).k = intNum();
			setState(45);
			match(T__6);
			 ((Int3Context)_localctx).r =  new org.callie.gen.Int3(((Int3Context)_localctx).i.r, ((Int3Context)_localctx).j.r, ((Int3Context)_localctx).k.r); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Float3Context extends ParserRuleContext {
		public org.callie.math.Vector3 r;
		public FloatNumContext i;
		public FloatNumContext j;
		public FloatNumContext k;
		public List<FloatNumContext> floatNum() {
			return getRuleContexts(FloatNumContext.class);
		}
		public FloatNumContext floatNum(int i) {
			return getRuleContext(FloatNumContext.class,i);
		}
		public Float3Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_float3; }
	}

	public final Float3Context float3() throws RecognitionException {
		Float3Context _localctx = new Float3Context(_ctx, getState());
		enterRule(_localctx, 4, RULE_float3);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(48);
			match(T__5);
			setState(49);
			((Float3Context)_localctx).i = floatNum();
			setState(50);
			match(T__1);
			setState(51);
			((Float3Context)_localctx).j = floatNum();
			setState(52);
			match(T__1);
			setState(53);
			((Float3Context)_localctx).k = floatNum();
			setState(54);
			match(T__6);
			 ((Float3Context)_localctx).r =  org.callie.math.Vector3.apply(((Float3Context)_localctx).i.r, ((Float3Context)_localctx).j.r, ((Float3Context)_localctx).k.r); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FloatNumContext extends ParserRuleContext {
		public float r;
		public Token s;
		public Token n;
		public Token m;
		public Token e;
		public Token p;
		public List<TerminalNode> DIGITS() { return getTokens(MapParser.DIGITS); }
		public TerminalNode DIGITS(int i) {
			return getToken(MapParser.DIGITS, i);
		}
		public FloatNumContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_floatNum; }
	}

	public final FloatNumContext floatNum() throws RecognitionException {
		FloatNumContext _localctx = new FloatNumContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_floatNum);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(58);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__7 || _la==T__8) {
				{
				setState(57);
				((FloatNumContext)_localctx).s = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==T__7 || _la==T__8) ) {
					((FloatNumContext)_localctx).s = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
			}

			setState(60);
			((FloatNumContext)_localctx).n = match(DIGITS);
			setState(63);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__9) {
				{
				setState(61);
				match(T__9);
				setState(62);
				((FloatNumContext)_localctx).m = match(DIGITS);
				}
			}

			setState(70);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__10 || _la==T__11) {
				{
				setState(65);
				_la = _input.LA(1);
				if ( !(_la==T__10 || _la==T__11) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(67);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__7 || _la==T__8) {
					{
					setState(66);
					((FloatNumContext)_localctx).e = _input.LT(1);
					_la = _input.LA(1);
					if ( !(_la==T__7 || _la==T__8) ) {
						((FloatNumContext)_localctx).e = (Token)_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
				}

				setState(69);
				((FloatNumContext)_localctx).p = match(DIGITS);
				}
			}


			        StringBuilder sb = new StringBuilder();
			        if((((FloatNumContext)_localctx).s!=null?((FloatNumContext)_localctx).s.getText():null) != null) sb.append((((FloatNumContext)_localctx).s!=null?((FloatNumContext)_localctx).s.getText():null));
			        sb.append((((FloatNumContext)_localctx).n!=null?((FloatNumContext)_localctx).n.getText():null));
			        if((((FloatNumContext)_localctx).m!=null?((FloatNumContext)_localctx).m.getText():null) != null){
			            sb.append('.').append((((FloatNumContext)_localctx).m!=null?((FloatNumContext)_localctx).m.getText():null));
			        }
			        if((((FloatNumContext)_localctx).p!=null?((FloatNumContext)_localctx).p.getText():null) != null){
			            sb.append('E');
			            if((((FloatNumContext)_localctx).e!=null?((FloatNumContext)_localctx).e.getText():null) != null) sb.append((((FloatNumContext)_localctx).e!=null?((FloatNumContext)_localctx).e.getText():null));
			            sb.append((((FloatNumContext)_localctx).p!=null?((FloatNumContext)_localctx).p.getText():null));
			        }
			        ((FloatNumContext)_localctx).r =  Float.parseFloat(sb.toString());
			    
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IntNumContext extends ParserRuleContext {
		public int r;
		public Token s;
		public Token n;
		public TerminalNode DIGITS() { return getToken(MapParser.DIGITS, 0); }
		public IntNumContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_intNum; }
	}

	public final IntNumContext intNum() throws RecognitionException {
		IntNumContext _localctx = new IntNumContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_intNum);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(75);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__7 || _la==T__8) {
				{
				setState(74);
				((IntNumContext)_localctx).s = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==T__7 || _la==T__8) ) {
					((IntNumContext)_localctx).s = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
			}

			setState(77);
			((IntNumContext)_localctx).n = match(DIGITS);
			 ((IntNumContext)_localctx).r =  Integer.parseInt((((IntNumContext)_localctx).s!=null?((IntNumContext)_localctx).s.getText():null) == null ? (((IntNumContext)_localctx).n!=null?((IntNumContext)_localctx).n.getText():null) : (((IntNumContext)_localctx).s!=null?((IntNumContext)_localctx).s.getText():null) + (((IntNumContext)_localctx).n!=null?((IntNumContext)_localctx).n.getText():null) ); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\20S\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\7\2\25\n\2"+
		"\f\2\16\2\30\13\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\7\2\"\n\2\f\2\16\2%"+
		"\13\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4"+
		"\3\4\3\4\3\4\3\4\3\4\3\5\5\5=\n\5\3\5\3\5\3\5\5\5B\n\5\3\5\3\5\5\5F\n"+
		"\5\3\5\5\5I\n\5\3\5\3\5\3\6\5\6N\n\6\3\6\3\6\3\6\3\6\2\2\7\2\4\6\b\n\2"+
		"\4\3\2\n\13\3\2\r\16\2T\2\f\3\2\2\2\4)\3\2\2\2\6\62\3\2\2\2\b<\3\2\2\2"+
		"\nM\3\2\2\2\f\r\b\2\1\2\r\16\7\3\2\2\16\17\5\6\4\2\17\26\b\2\1\2\20\21"+
		"\7\4\2\2\21\22\5\6\4\2\22\23\b\2\1\2\23\25\3\2\2\2\24\20\3\2\2\2\25\30"+
		"\3\2\2\2\26\24\3\2\2\2\26\27\3\2\2\2\27\31\3\2\2\2\30\26\3\2\2\2\31\32"+
		"\7\5\2\2\32\33\7\6\2\2\33\34\5\4\3\2\34#\b\2\1\2\35\36\7\4\2\2\36\37\5"+
		"\4\3\2\37 \b\2\1\2 \"\3\2\2\2!\35\3\2\2\2\"%\3\2\2\2#!\3\2\2\2#$\3\2\2"+
		"\2$&\3\2\2\2%#\3\2\2\2&\'\7\7\2\2\'(\b\2\1\2(\3\3\2\2\2)*\7\b\2\2*+\5"+
		"\n\6\2+,\7\4\2\2,-\5\n\6\2-.\7\4\2\2./\5\n\6\2/\60\7\t\2\2\60\61\b\3\1"+
		"\2\61\5\3\2\2\2\62\63\7\b\2\2\63\64\5\b\5\2\64\65\7\4\2\2\65\66\5\b\5"+
		"\2\66\67\7\4\2\2\678\5\b\5\289\7\t\2\29:\b\4\1\2:\7\3\2\2\2;=\t\2\2\2"+
		"<;\3\2\2\2<=\3\2\2\2=>\3\2\2\2>A\7\17\2\2?@\7\f\2\2@B\7\17\2\2A?\3\2\2"+
		"\2AB\3\2\2\2BH\3\2\2\2CE\t\3\2\2DF\t\2\2\2ED\3\2\2\2EF\3\2\2\2FG\3\2\2"+
		"\2GI\7\17\2\2HC\3\2\2\2HI\3\2\2\2IJ\3\2\2\2JK\b\5\1\2K\t\3\2\2\2LN\t\2"+
		"\2\2ML\3\2\2\2MN\3\2\2\2NO\3\2\2\2OP\7\17\2\2PQ\b\6\1\2Q\13\3\2\2\2\t"+
		"\26#<AEHM";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}