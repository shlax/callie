// Generated from /home/pochodnicky/wrksp/no-git/callie/callie-scala/src/g4/Map.g4 by ANTLR 4.10.1
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
	static { RuntimeMetaData.checkVersion("4.10.1", RuntimeMetaData.VERSION); }

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
		public org.callie.map.MapData result;
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
			        java.util.ArrayList<org.callie.map.IndexTriangle> li = new java.util.ArrayList<>();
			     
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
			 ((MapContext)_localctx).result =  new org.callie.map.MapData(lf.toArray(new org.callie.math.Vector3[0]), li.toArray(new org.callie.map.IndexTriangle[0])); 
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
		public org.callie.map.IndexTriangle r;
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
			 ((Int3Context)_localctx).r =  new org.callie.map.IndexTriangle(((Int3Context)_localctx).i.r, ((Int3Context)_localctx).j.r, ((Int3Context)_localctx).k.r); 
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
		"\u0004\u0001\u000eQ\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0001"+
		"\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001"+
		"\u0000\u0001\u0000\u0005\u0000\u0013\b\u0000\n\u0000\f\u0000\u0016\t\u0000"+
		"\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0000\u0005\u0000 \b\u0000\n\u0000\f\u0000#\t\u0000"+
		"\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0003\u0003\u0003;\b\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0003\u0003@\b\u0003\u0001\u0003"+
		"\u0001\u0003\u0003\u0003D\b\u0003\u0001\u0003\u0003\u0003G\b\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0004\u0003\u0004L\b\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0000\u0000\u0005\u0000\u0002\u0004\u0006"+
		"\b\u0000\u0002\u0001\u0000\b\t\u0001\u0000\u000b\fR\u0000\n\u0001\u0000"+
		"\u0000\u0000\u0002\'\u0001\u0000\u0000\u0000\u00040\u0001\u0000\u0000"+
		"\u0000\u0006:\u0001\u0000\u0000\u0000\bK\u0001\u0000\u0000\u0000\n\u000b"+
		"\u0006\u0000\uffff\uffff\u0000\u000b\f\u0005\u0001\u0000\u0000\f\r\u0003"+
		"\u0004\u0002\u0000\r\u0014\u0006\u0000\uffff\uffff\u0000\u000e\u000f\u0005"+
		"\u0002\u0000\u0000\u000f\u0010\u0003\u0004\u0002\u0000\u0010\u0011\u0006"+
		"\u0000\uffff\uffff\u0000\u0011\u0013\u0001\u0000\u0000\u0000\u0012\u000e"+
		"\u0001\u0000\u0000\u0000\u0013\u0016\u0001\u0000\u0000\u0000\u0014\u0012"+
		"\u0001\u0000\u0000\u0000\u0014\u0015\u0001\u0000\u0000\u0000\u0015\u0017"+
		"\u0001\u0000\u0000\u0000\u0016\u0014\u0001\u0000\u0000\u0000\u0017\u0018"+
		"\u0005\u0003\u0000\u0000\u0018\u0019\u0005\u0004\u0000\u0000\u0019\u001a"+
		"\u0003\u0002\u0001\u0000\u001a!\u0006\u0000\uffff\uffff\u0000\u001b\u001c"+
		"\u0005\u0002\u0000\u0000\u001c\u001d\u0003\u0002\u0001\u0000\u001d\u001e"+
		"\u0006\u0000\uffff\uffff\u0000\u001e \u0001\u0000\u0000\u0000\u001f\u001b"+
		"\u0001\u0000\u0000\u0000 #\u0001\u0000\u0000\u0000!\u001f\u0001\u0000"+
		"\u0000\u0000!\"\u0001\u0000\u0000\u0000\"$\u0001\u0000\u0000\u0000#!\u0001"+
		"\u0000\u0000\u0000$%\u0005\u0005\u0000\u0000%&\u0006\u0000\uffff\uffff"+
		"\u0000&\u0001\u0001\u0000\u0000\u0000\'(\u0005\u0006\u0000\u0000()\u0003"+
		"\b\u0004\u0000)*\u0005\u0002\u0000\u0000*+\u0003\b\u0004\u0000+,\u0005"+
		"\u0002\u0000\u0000,-\u0003\b\u0004\u0000-.\u0005\u0007\u0000\u0000./\u0006"+
		"\u0001\uffff\uffff\u0000/\u0003\u0001\u0000\u0000\u000001\u0005\u0006"+
		"\u0000\u000012\u0003\u0006\u0003\u000023\u0005\u0002\u0000\u000034\u0003"+
		"\u0006\u0003\u000045\u0005\u0002\u0000\u000056\u0003\u0006\u0003\u0000"+
		"67\u0005\u0007\u0000\u000078\u0006\u0002\uffff\uffff\u00008\u0005\u0001"+
		"\u0000\u0000\u00009;\u0007\u0000\u0000\u0000:9\u0001\u0000\u0000\u0000"+
		":;\u0001\u0000\u0000\u0000;<\u0001\u0000\u0000\u0000<?\u0005\r\u0000\u0000"+
		"=>\u0005\n\u0000\u0000>@\u0005\r\u0000\u0000?=\u0001\u0000\u0000\u0000"+
		"?@\u0001\u0000\u0000\u0000@F\u0001\u0000\u0000\u0000AC\u0007\u0001\u0000"+
		"\u0000BD\u0007\u0000\u0000\u0000CB\u0001\u0000\u0000\u0000CD\u0001\u0000"+
		"\u0000\u0000DE\u0001\u0000\u0000\u0000EG\u0005\r\u0000\u0000FA\u0001\u0000"+
		"\u0000\u0000FG\u0001\u0000\u0000\u0000GH\u0001\u0000\u0000\u0000HI\u0006"+
		"\u0003\uffff\uffff\u0000I\u0007\u0001\u0000\u0000\u0000JL\u0007\u0000"+
		"\u0000\u0000KJ\u0001\u0000\u0000\u0000KL\u0001\u0000\u0000\u0000LM\u0001"+
		"\u0000\u0000\u0000MN\u0005\r\u0000\u0000NO\u0006\u0004\uffff\uffff\u0000"+
		"O\t\u0001\u0000\u0000\u0000\u0007\u0014!:?CFK";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}