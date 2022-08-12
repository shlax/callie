// Generated from /home/pochodnicky/wrksp/no-git/callie/callie-scala/src/g4/KeyFrame.g4 by ANTLR 4.10.1
package org.callie.gen.keyFrame;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class KeyFrameParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.10.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, NAME=12, DIGITS=13, WS=14;
	public static final int
		RULE_mainNode = 0, RULE_node = 1, RULE_vector = 2, RULE_floatNum = 3;
	private static String[] makeRuleNames() {
		return new String[] {
			"mainNode", "node", "vector", "floatNum"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'['", "':'", "']'", "'('", "','", "')'", "'+'", "'-'", "'.'", 
			"'e'", "'E'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			"NAME", "DIGITS", "WS"
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
	public String getGrammarFileName() { return "KeyFrame.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }


	  float scale = 1f;

	  public void setScale(float x){
	    scale = x;
	  }

	public KeyFrameParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class MainNodeContext extends ParserRuleContext {
		public org.callie.ringing.KeyFrameLoader.MainNodeKeys result;
		public Token n;
		public VectorContext o;
		public VectorContext a;
		public NodeContext i;
		public TerminalNode NAME() { return getToken(KeyFrameParser.NAME, 0); }
		public List<VectorContext> vector() {
			return getRuleContexts(VectorContext.class);
		}
		public VectorContext vector(int i) {
			return getRuleContext(VectorContext.class,i);
		}
		public List<NodeContext> node() {
			return getRuleContexts(NodeContext.class);
		}
		public NodeContext node(int i) {
			return getRuleContext(NodeContext.class,i);
		}
		public MainNodeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mainNode; }
	}

	public final MainNodeContext mainNode() throws RecognitionException {
		MainNodeContext _localctx = new MainNodeContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_mainNode);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			 java.util.List<org.callie.ringing.KeyFrameLoader.NodeKeys> l = new java.util.ArrayList<>(); 
			setState(9);
			match(T__0);
			setState(10);
			((MainNodeContext)_localctx).n = match(NAME);
			setState(11);
			match(T__1);
			setState(12);
			((MainNodeContext)_localctx).o = vector();
			setState(13);
			match(T__1);
			setState(14);
			((MainNodeContext)_localctx).a = vector();
			setState(20);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(15);
				((MainNodeContext)_localctx).i = node();
				l.add(((MainNodeContext)_localctx).i.r);
				}
				}
				setState(22);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(23);
			match(T__2);
			 ((MainNodeContext)_localctx).result =  org.callie.ringing.KeyFrameLoader.create((((MainNodeContext)_localctx).n!=null?((MainNodeContext)_localctx).n.getText():null), ((MainNodeContext)_localctx).o.r.mul(scale), ((MainNodeContext)_localctx).a.r, l);  
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

	public static class NodeContext extends ParserRuleContext {
		public org.callie.ringing.KeyFrameLoader.NodeKeys r;
		public Token n;
		public VectorContext v;
		public NodeContext i;
		public TerminalNode NAME() { return getToken(KeyFrameParser.NAME, 0); }
		public VectorContext vector() {
			return getRuleContext(VectorContext.class,0);
		}
		public List<NodeContext> node() {
			return getRuleContexts(NodeContext.class);
		}
		public NodeContext node(int i) {
			return getRuleContext(NodeContext.class,i);
		}
		public NodeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_node; }
	}

	public final NodeContext node() throws RecognitionException {
		NodeContext _localctx = new NodeContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_node);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			 java.util.List<org.callie.ringing.KeyFrameLoader.NodeKeys> l = new java.util.ArrayList<>(); 
			setState(27);
			match(T__0);
			setState(28);
			((NodeContext)_localctx).n = match(NAME);
			setState(29);
			match(T__1);
			setState(30);
			((NodeContext)_localctx).v = vector();
			setState(36);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(31);
				((NodeContext)_localctx).i = node();
				l.add(((NodeContext)_localctx).i.r);
				}
				}
				setState(38);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(39);
			match(T__2);
			 ((NodeContext)_localctx).r =  org.callie.ringing.KeyFrameLoader.create((((NodeContext)_localctx).n!=null?((NodeContext)_localctx).n.getText():null), ((NodeContext)_localctx).v.r, l);  
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

	public static class VectorContext extends ParserRuleContext {
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
		public VectorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_vector; }
	}

	public final VectorContext vector() throws RecognitionException {
		VectorContext _localctx = new VectorContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_vector);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(42);
			match(T__3);
			setState(43);
			((VectorContext)_localctx).i = floatNum();
			setState(44);
			match(T__4);
			setState(45);
			((VectorContext)_localctx).j = floatNum();
			setState(46);
			match(T__4);
			setState(47);
			((VectorContext)_localctx).k = floatNum();
			setState(48);
			match(T__5);
			 ((VectorContext)_localctx).r =  org.callie.math.Vector3.apply(((VectorContext)_localctx).i.r, ((VectorContext)_localctx).j.r, ((VectorContext)_localctx).k.r); 
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
		public List<TerminalNode> DIGITS() { return getTokens(KeyFrameParser.DIGITS); }
		public TerminalNode DIGITS(int i) {
			return getToken(KeyFrameParser.DIGITS, i);
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
			setState(52);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__6 || _la==T__7) {
				{
				setState(51);
				((FloatNumContext)_localctx).s = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==T__6 || _la==T__7) ) {
					((FloatNumContext)_localctx).s = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
			}

			setState(54);
			((FloatNumContext)_localctx).n = match(DIGITS);
			setState(57);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__8) {
				{
				setState(55);
				match(T__8);
				setState(56);
				((FloatNumContext)_localctx).m = match(DIGITS);
				}
			}

			setState(64);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__9 || _la==T__10) {
				{
				setState(59);
				_la = _input.LA(1);
				if ( !(_la==T__9 || _la==T__10) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(61);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__6 || _la==T__7) {
					{
					setState(60);
					((FloatNumContext)_localctx).e = _input.LT(1);
					_la = _input.LA(1);
					if ( !(_la==T__6 || _la==T__7) ) {
						((FloatNumContext)_localctx).e = (Token)_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
				}

				setState(63);
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

	public static final String _serializedATN =
		"\u0004\u0001\u000eE\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0001\u0000\u0001\u0000\u0001"+
		"\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001"+
		"\u0000\u0001\u0000\u0005\u0000\u0013\b\u0000\n\u0000\f\u0000\u0016\t\u0000"+
		"\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0005\u0001"+
		"#\b\u0001\n\u0001\f\u0001&\t\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0003\u0003\u00035\b\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0003\u0003:\b\u0003\u0001\u0003"+
		"\u0001\u0003\u0003\u0003>\b\u0003\u0001\u0003\u0003\u0003A\b\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0000\u0000\u0004\u0000\u0002\u0004\u0006"+
		"\u0000\u0002\u0001\u0000\u0007\b\u0001\u0000\n\u000bF\u0000\b\u0001\u0000"+
		"\u0000\u0000\u0002\u001a\u0001\u0000\u0000\u0000\u0004*\u0001\u0000\u0000"+
		"\u0000\u00064\u0001\u0000\u0000\u0000\b\t\u0006\u0000\uffff\uffff\u0000"+
		"\t\n\u0005\u0001\u0000\u0000\n\u000b\u0005\f\u0000\u0000\u000b\f\u0005"+
		"\u0002\u0000\u0000\f\r\u0003\u0004\u0002\u0000\r\u000e\u0005\u0002\u0000"+
		"\u0000\u000e\u0014\u0003\u0004\u0002\u0000\u000f\u0010\u0003\u0002\u0001"+
		"\u0000\u0010\u0011\u0006\u0000\uffff\uffff\u0000\u0011\u0013\u0001\u0000"+
		"\u0000\u0000\u0012\u000f\u0001\u0000\u0000\u0000\u0013\u0016\u0001\u0000"+
		"\u0000\u0000\u0014\u0012\u0001\u0000\u0000\u0000\u0014\u0015\u0001\u0000"+
		"\u0000\u0000\u0015\u0017\u0001\u0000\u0000\u0000\u0016\u0014\u0001\u0000"+
		"\u0000\u0000\u0017\u0018\u0005\u0003\u0000\u0000\u0018\u0019\u0006\u0000"+
		"\uffff\uffff\u0000\u0019\u0001\u0001\u0000\u0000\u0000\u001a\u001b\u0006"+
		"\u0001\uffff\uffff\u0000\u001b\u001c\u0005\u0001\u0000\u0000\u001c\u001d"+
		"\u0005\f\u0000\u0000\u001d\u001e\u0005\u0002\u0000\u0000\u001e$\u0003"+
		"\u0004\u0002\u0000\u001f \u0003\u0002\u0001\u0000 !\u0006\u0001\uffff"+
		"\uffff\u0000!#\u0001\u0000\u0000\u0000\"\u001f\u0001\u0000\u0000\u0000"+
		"#&\u0001\u0000\u0000\u0000$\"\u0001\u0000\u0000\u0000$%\u0001\u0000\u0000"+
		"\u0000%\'\u0001\u0000\u0000\u0000&$\u0001\u0000\u0000\u0000\'(\u0005\u0003"+
		"\u0000\u0000()\u0006\u0001\uffff\uffff\u0000)\u0003\u0001\u0000\u0000"+
		"\u0000*+\u0005\u0004\u0000\u0000+,\u0003\u0006\u0003\u0000,-\u0005\u0005"+
		"\u0000\u0000-.\u0003\u0006\u0003\u0000./\u0005\u0005\u0000\u0000/0\u0003"+
		"\u0006\u0003\u000001\u0005\u0006\u0000\u000012\u0006\u0002\uffff\uffff"+
		"\u00002\u0005\u0001\u0000\u0000\u000035\u0007\u0000\u0000\u000043\u0001"+
		"\u0000\u0000\u000045\u0001\u0000\u0000\u000056\u0001\u0000\u0000\u0000"+
		"69\u0005\r\u0000\u000078\u0005\t\u0000\u00008:\u0005\r\u0000\u000097\u0001"+
		"\u0000\u0000\u00009:\u0001\u0000\u0000\u0000:@\u0001\u0000\u0000\u0000"+
		";=\u0007\u0001\u0000\u0000<>\u0007\u0000\u0000\u0000=<\u0001\u0000\u0000"+
		"\u0000=>\u0001\u0000\u0000\u0000>?\u0001\u0000\u0000\u0000?A\u0005\r\u0000"+
		"\u0000@;\u0001\u0000\u0000\u0000@A\u0001\u0000\u0000\u0000AB\u0001\u0000"+
		"\u0000\u0000BC\u0006\u0003\uffff\uffff\u0000C\u0007\u0001\u0000\u0000"+
		"\u0000\u0006\u0014$49=@";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}