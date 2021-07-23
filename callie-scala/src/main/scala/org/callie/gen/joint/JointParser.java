// Generated from /home/pochodnicky/wrksp/no-git/callie/callie-scala/src/g4/Joint.g4 by ANTLR 4.9.1
package org.callie.gen.joint;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class JointParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.9.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, NAME=12, DIGITS=13, WS=14;
	public static final int
		RULE_groupMap = 0, RULE_group = 1, RULE_vector = 2, RULE_floatNum = 3, 
		RULE_intNum = 4;
	private static String[] makeRuleNames() {
		return new String[] {
			"groupMap", "group", "vector", "floatNum", "intNum"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'{'", "','", "'}'", "':'", "'('", "')'", "'+'", "'-'", "'.'", 
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
	public String getGrammarFileName() { return "Joint.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }


	  float scale = 1f;

	public JointParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class GroupMapContext extends ParserRuleContext {
		public java.util.Map<String, java.util.Set<Integer>> r;
		public GroupContext g;
		public GroupContext gn;
		public List<GroupContext> group() {
			return getRuleContexts(GroupContext.class);
		}
		public GroupContext group(int i) {
			return getRuleContext(GroupContext.class,i);
		}
		public GroupMapContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_groupMap; }
	}

	public final GroupMapContext groupMap() throws RecognitionException {
		GroupMapContext _localctx = new GroupMapContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_groupMap);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			 ((GroupMapContext)_localctx).r =  new java.util.HashMap<>(); 
			setState(11);
			match(T__0);
			setState(12);
			((GroupMapContext)_localctx).g = group();

			        _localctx.r.computeIfAbsent( ((GroupMapContext)_localctx).g.r.nm(), x -> new java.util.HashSet<Integer>() ).addAll( ((GroupMapContext)_localctx).g.r.ind() );
			    
			setState(20);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__1) {
				{
				{
				setState(14);
				match(T__1);
				setState(15);
				((GroupMapContext)_localctx).gn = group();

				        _localctx.r.computeIfAbsent( ((GroupMapContext)_localctx).gn.r.nm(), x -> new java.util.HashSet<Integer>() ).addAll( ((GroupMapContext)_localctx).gn.r.ind() );
				    
				}
				}
				setState(22);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(23);
			match(T__2);
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

	public static class GroupContext extends ParserRuleContext {
		public org.callie.ringing.Group r;
		public Token n;
		public IntNumContext i;
		public IntNumContext in;
		public TerminalNode NAME() { return getToken(JointParser.NAME, 0); }
		public List<IntNumContext> intNum() {
			return getRuleContexts(IntNumContext.class);
		}
		public IntNumContext intNum(int i) {
			return getRuleContext(IntNumContext.class,i);
		}
		public GroupContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_group; }
	}

	public final GroupContext group() throws RecognitionException {
		GroupContext _localctx = new GroupContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_group);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			 java.util.Set<Integer> li = new java.util.HashSet<>(); 
			setState(26);
			((GroupContext)_localctx).n = match(NAME);
			setState(27);
			match(T__3);
			setState(28);
			match(T__4);
			setState(29);
			((GroupContext)_localctx).i = intNum();
			 li.add(((GroupContext)_localctx).i.r); 
			setState(36);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__6) | (1L << T__7) | (1L << DIGITS))) != 0)) {
				{
				{
				setState(31);
				((GroupContext)_localctx).in = intNum();
				 li.add(((GroupContext)_localctx).in.r); 
				}
				}
				setState(38);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(39);
			match(T__5);
			 new org.callie.ringing.Group( (((GroupContext)_localctx).n!=null?((GroupContext)_localctx).n.getText():null),  li ); 
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
			match(T__4);
			setState(43);
			((VectorContext)_localctx).i = floatNum();
			setState(44);
			match(T__1);
			setState(45);
			((VectorContext)_localctx).j = floatNum();
			setState(46);
			match(T__1);
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
		public List<TerminalNode> DIGITS() { return getTokens(JointParser.DIGITS); }
		public TerminalNode DIGITS(int i) {
			return getToken(JointParser.DIGITS, i);
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

	public static class IntNumContext extends ParserRuleContext {
		public int r;
		public Token s;
		public Token n;
		public TerminalNode DIGITS() { return getToken(JointParser.DIGITS, 0); }
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
			setState(69);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__6 || _la==T__7) {
				{
				setState(68);
				((IntNumContext)_localctx).s = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==T__6 || _la==T__7) ) {
					((IntNumContext)_localctx).s = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
			}

			setState(71);
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\20M\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\7\2\25\n\2"+
		"\f\2\16\2\30\13\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\7\3%\n\3"+
		"\f\3\16\3(\13\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\5\5"+
		"\5\67\n\5\3\5\3\5\3\5\5\5<\n\5\3\5\3\5\5\5@\n\5\3\5\5\5C\n\5\3\5\3\5\3"+
		"\6\5\6H\n\6\3\6\3\6\3\6\3\6\2\2\7\2\4\6\b\n\2\4\3\2\t\n\3\2\f\r\2N\2\f"+
		"\3\2\2\2\4\33\3\2\2\2\6,\3\2\2\2\b\66\3\2\2\2\nG\3\2\2\2\f\r\b\2\1\2\r"+
		"\16\7\3\2\2\16\17\5\4\3\2\17\26\b\2\1\2\20\21\7\4\2\2\21\22\5\4\3\2\22"+
		"\23\b\2\1\2\23\25\3\2\2\2\24\20\3\2\2\2\25\30\3\2\2\2\26\24\3\2\2\2\26"+
		"\27\3\2\2\2\27\31\3\2\2\2\30\26\3\2\2\2\31\32\7\5\2\2\32\3\3\2\2\2\33"+
		"\34\b\3\1\2\34\35\7\16\2\2\35\36\7\6\2\2\36\37\7\7\2\2\37 \5\n\6\2 &\b"+
		"\3\1\2!\"\5\n\6\2\"#\b\3\1\2#%\3\2\2\2$!\3\2\2\2%(\3\2\2\2&$\3\2\2\2&"+
		"\'\3\2\2\2\')\3\2\2\2(&\3\2\2\2)*\7\b\2\2*+\b\3\1\2+\5\3\2\2\2,-\7\7\2"+
		"\2-.\5\b\5\2./\7\4\2\2/\60\5\b\5\2\60\61\7\4\2\2\61\62\5\b\5\2\62\63\7"+
		"\b\2\2\63\64\b\4\1\2\64\7\3\2\2\2\65\67\t\2\2\2\66\65\3\2\2\2\66\67\3"+
		"\2\2\2\678\3\2\2\28;\7\17\2\29:\7\13\2\2:<\7\17\2\2;9\3\2\2\2;<\3\2\2"+
		"\2<B\3\2\2\2=?\t\3\2\2>@\t\2\2\2?>\3\2\2\2?@\3\2\2\2@A\3\2\2\2AC\7\17"+
		"\2\2B=\3\2\2\2BC\3\2\2\2CD\3\2\2\2DE\b\5\1\2E\t\3\2\2\2FH\t\2\2\2GF\3"+
		"\2\2\2GH\3\2\2\2HI\3\2\2\2IJ\7\17\2\2JK\b\6\1\2K\13\3\2\2\2\t\26&\66;"+
		"?BG";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}