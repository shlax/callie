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
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, NAME=15, DIGITS=16, WS=17;
	public static final int
		RULE_node = 0, RULE_linMap = 1, RULE_linear = 2, RULE_normal = 3, RULE_groupMap = 4, 
		RULE_group = 5, RULE_vector = 6, RULE_floatNum = 7, RULE_intNum = 8;
	private static String[] makeRuleNames() {
		return new String[] {
			"node", "linMap", "linear", "normal", "groupMap", "group", "vector", 
			"floatNum", "intNum"
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

	@Override
	public String getGrammarFileName() { return "Joint.g4"; }

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

	public JointParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class NodeContext extends ParserRuleContext {
		public org.callie.ringing.Node result;
		public LinearContext l;
		public NormalContext n;
		public LinearContext linear() {
			return getRuleContext(LinearContext.class,0);
		}
		public NormalContext normal() {
			return getRuleContext(NormalContext.class,0);
		}
		public NodeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_node; }
	}

	public final NodeContext node() throws RecognitionException {
		NodeContext _localctx = new NodeContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_node);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(18);
			match(T__0);
			setState(25);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				{
				{
				setState(19);
				((NodeContext)_localctx).l = linear();
				 ((NodeContext)_localctx).result =  ((NodeContext)_localctx).l.r; 
				}
				}
				break;
			case 2:
				{
				{
				setState(22);
				((NodeContext)_localctx).n = normal();
				 ((NodeContext)_localctx).result =  ((NodeContext)_localctx).n.r; 
				}
				}
				break;
			}
			setState(27);
			match(T__1);
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

	public static class LinMapContext extends ParserRuleContext {
		public org.callie.ringing.LinMap r;
		public Token f;
		public TerminalNode NAME() { return getToken(JointParser.NAME, 0); }
		public LinMapContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_linMap; }
	}

	public final LinMapContext linMap() throws RecognitionException {
		LinMapContext _localctx = new LinMapContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_linMap);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(29);
			match(T__2);
			setState(30);
			((LinMapContext)_localctx).f = match(NAME);
			 ((LinMapContext)_localctx).r =  new org.callie.ringing.LinMap((((LinMapContext)_localctx).f!=null?((LinMapContext)_localctx).f.getText():null)); 
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

	public static class LinearContext extends ParserRuleContext {
		public org.callie.ringing.LinNode r;
		public Token n;
		public VectorContext v;
		public LinMapContext l;
		public GroupMapContext gm;
		public TerminalNode NAME() { return getToken(JointParser.NAME, 0); }
		public VectorContext vector() {
			return getRuleContext(VectorContext.class,0);
		}
		public GroupMapContext groupMap() {
			return getRuleContext(GroupMapContext.class,0);
		}
		public List<LinMapContext> linMap() {
			return getRuleContexts(LinMapContext.class);
		}
		public LinMapContext linMap(int i) {
			return getRuleContext(LinMapContext.class,i);
		}
		public LinearContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_linear; }
	}

	public final LinearContext linear() throws RecognitionException {
		LinearContext _localctx = new LinearContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_linear);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			 java.util.List<org.callie.ringing.LinMap> l = new java.util.ArrayList<>(); 
			setState(34);
			((LinearContext)_localctx).n = match(NAME);
			setState(35);
			match(T__2);
			setState(36);
			((LinearContext)_localctx).v = vector();
			{
			setState(42);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__2) {
				{
				{
				setState(37);
				((LinearContext)_localctx).l = linMap();
				 l.add(((LinearContext)_localctx).l.r); 
				}
				}
				setState(44);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
			setState(45);
			match(T__3);
			setState(46);
			((LinearContext)_localctx).gm = groupMap();
			 ((LinearContext)_localctx).r =  org.callie.ringing.LinNode.create( (((LinearContext)_localctx).n!=null?((LinearContext)_localctx).n.getText():null), ((LinearContext)_localctx).v.r,l, ((LinearContext)_localctx).gm.r ); 
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

	public static class NormalContext extends ParserRuleContext {
		public org.callie.ringing.IntNode r;
		public Token n;
		public VectorContext v;
		public GroupMapContext gm;
		public NodeContext nd;
		public TerminalNode NAME() { return getToken(JointParser.NAME, 0); }
		public VectorContext vector() {
			return getRuleContext(VectorContext.class,0);
		}
		public GroupMapContext groupMap() {
			return getRuleContext(GroupMapContext.class,0);
		}
		public List<NodeContext> node() {
			return getRuleContexts(NodeContext.class);
		}
		public NodeContext node(int i) {
			return getRuleContext(NodeContext.class,i);
		}
		public NormalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_normal; }
	}

	public final NormalContext normal() throws RecognitionException {
		NormalContext _localctx = new NormalContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_normal);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			 java.util.List<org.callie.ringing.Node> l = new java.util.ArrayList<>(); 
			setState(50);
			((NormalContext)_localctx).n = match(NAME);
			setState(51);
			match(T__3);
			setState(52);
			((NormalContext)_localctx).v = vector();
			setState(53);
			match(T__3);
			setState(54);
			((NormalContext)_localctx).gm = groupMap();
			{
			setState(60);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(55);
				((NormalContext)_localctx).nd = node();
				 l.add( ((NormalContext)_localctx).nd.result ); 
				}
				}
				setState(62);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
			 ((NormalContext)_localctx).r =  org.callie.ringing.IntNode.create((((NormalContext)_localctx).n!=null?((NormalContext)_localctx).n.getText():null), ((NormalContext)_localctx).v.r.mul(scale), ((NormalContext)_localctx).gm.r, l); 
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
		enterRule(_localctx, 8, RULE_groupMap);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			 ((GroupMapContext)_localctx).r =  new java.util.HashMap<>(); 
			setState(66);
			match(T__4);
			setState(67);
			((GroupMapContext)_localctx).g = group();

			        _localctx.r.computeIfAbsent( ((GroupMapContext)_localctx).g.r.nm(), x -> new java.util.HashSet<Integer>() ).addAll( ((GroupMapContext)_localctx).g.r.ind() );
			    
			setState(75);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__5) {
				{
				{
				setState(69);
				match(T__5);
				setState(70);
				((GroupMapContext)_localctx).gn = group();

				        _localctx.r.computeIfAbsent( ((GroupMapContext)_localctx).gn.r.nm(), x -> new java.util.HashSet<Integer>() ).addAll( ((GroupMapContext)_localctx).gn.r.ind() );
				    
				}
				}
				setState(77);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(78);
			match(T__6);
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
		enterRule(_localctx, 10, RULE_group);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			 java.util.Set<Integer> li = new java.util.HashSet<>(); 
			setState(81);
			((GroupContext)_localctx).n = match(NAME);
			setState(82);
			match(T__3);
			setState(83);
			match(T__7);
			setState(84);
			((GroupContext)_localctx).i = intNum();
			 li.add(((GroupContext)_localctx).i.r); 
			{
			setState(92);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__5) {
				{
				{
				setState(86);
				match(T__5);
				setState(87);
				((GroupContext)_localctx).in = intNum();
				 li.add(((GroupContext)_localctx).in.r); 
				}
				}
				setState(94);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
			setState(95);
			match(T__8);
			 ((GroupContext)_localctx).r =  new org.callie.ringing.Group( (((GroupContext)_localctx).n!=null?((GroupContext)_localctx).n.getText():null),  li ); 
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
		enterRule(_localctx, 12, RULE_vector);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(98);
			match(T__7);
			setState(99);
			((VectorContext)_localctx).i = floatNum();
			setState(100);
			match(T__5);
			setState(101);
			((VectorContext)_localctx).j = floatNum();
			setState(102);
			match(T__5);
			setState(103);
			((VectorContext)_localctx).k = floatNum();
			setState(104);
			match(T__8);
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
		enterRule(_localctx, 14, RULE_floatNum);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(108);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__9 || _la==T__10) {
				{
				setState(107);
				((FloatNumContext)_localctx).s = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==T__9 || _la==T__10) ) {
					((FloatNumContext)_localctx).s = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
			}

			setState(110);
			((FloatNumContext)_localctx).n = match(DIGITS);
			setState(113);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__11) {
				{
				setState(111);
				match(T__11);
				setState(112);
				((FloatNumContext)_localctx).m = match(DIGITS);
				}
			}

			setState(120);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__12 || _la==T__13) {
				{
				setState(115);
				_la = _input.LA(1);
				if ( !(_la==T__12 || _la==T__13) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(117);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__9 || _la==T__10) {
					{
					setState(116);
					((FloatNumContext)_localctx).e = _input.LT(1);
					_la = _input.LA(1);
					if ( !(_la==T__9 || _la==T__10) ) {
						((FloatNumContext)_localctx).e = (Token)_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
				}

				setState(119);
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
		enterRule(_localctx, 16, RULE_intNum);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(125);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__9 || _la==T__10) {
				{
				setState(124);
				((IntNumContext)_localctx).s = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==T__9 || _la==T__10) ) {
					((IntNumContext)_localctx).s = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
			}

			setState(127);
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\23\u0085\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\3\2\3"+
		"\2\3\2\3\2\3\2\3\2\3\2\5\2\34\n\2\3\2\3\2\3\3\3\3\3\3\3\3\3\4\3\4\3\4"+
		"\3\4\3\4\3\4\3\4\7\4+\n\4\f\4\16\4.\13\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3"+
		"\5\3\5\3\5\3\5\3\5\3\5\7\5=\n\5\f\5\16\5@\13\5\3\5\3\5\3\6\3\6\3\6\3\6"+
		"\3\6\3\6\3\6\3\6\7\6L\n\6\f\6\16\6O\13\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3"+
		"\7\3\7\3\7\3\7\3\7\7\7]\n\7\f\7\16\7`\13\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b"+
		"\3\b\3\b\3\b\3\b\3\b\3\t\5\to\n\t\3\t\3\t\3\t\5\tt\n\t\3\t\3\t\5\tx\n"+
		"\t\3\t\5\t{\n\t\3\t\3\t\3\n\5\n\u0080\n\n\3\n\3\n\3\n\3\n\2\2\13\2\4\6"+
		"\b\n\f\16\20\22\2\4\3\2\f\r\3\2\17\20\2\u0085\2\24\3\2\2\2\4\37\3\2\2"+
		"\2\6#\3\2\2\2\b\63\3\2\2\2\nC\3\2\2\2\fR\3\2\2\2\16d\3\2\2\2\20n\3\2\2"+
		"\2\22\177\3\2\2\2\24\33\7\3\2\2\25\26\5\6\4\2\26\27\b\2\1\2\27\34\3\2"+
		"\2\2\30\31\5\b\5\2\31\32\b\2\1\2\32\34\3\2\2\2\33\25\3\2\2\2\33\30\3\2"+
		"\2\2\34\35\3\2\2\2\35\36\7\4\2\2\36\3\3\2\2\2\37 \7\5\2\2 !\7\21\2\2!"+
		"\"\b\3\1\2\"\5\3\2\2\2#$\b\4\1\2$%\7\21\2\2%&\7\5\2\2&,\5\16\b\2\'(\5"+
		"\4\3\2()\b\4\1\2)+\3\2\2\2*\'\3\2\2\2+.\3\2\2\2,*\3\2\2\2,-\3\2\2\2-/"+
		"\3\2\2\2.,\3\2\2\2/\60\7\6\2\2\60\61\5\n\6\2\61\62\b\4\1\2\62\7\3\2\2"+
		"\2\63\64\b\5\1\2\64\65\7\21\2\2\65\66\7\6\2\2\66\67\5\16\b\2\678\7\6\2"+
		"\28>\5\n\6\29:\5\2\2\2:;\b\5\1\2;=\3\2\2\2<9\3\2\2\2=@\3\2\2\2><\3\2\2"+
		"\2>?\3\2\2\2?A\3\2\2\2@>\3\2\2\2AB\b\5\1\2B\t\3\2\2\2CD\b\6\1\2DE\7\7"+
		"\2\2EF\5\f\7\2FM\b\6\1\2GH\7\b\2\2HI\5\f\7\2IJ\b\6\1\2JL\3\2\2\2KG\3\2"+
		"\2\2LO\3\2\2\2MK\3\2\2\2MN\3\2\2\2NP\3\2\2\2OM\3\2\2\2PQ\7\t\2\2Q\13\3"+
		"\2\2\2RS\b\7\1\2ST\7\21\2\2TU\7\6\2\2UV\7\n\2\2VW\5\22\n\2W^\b\7\1\2X"+
		"Y\7\b\2\2YZ\5\22\n\2Z[\b\7\1\2[]\3\2\2\2\\X\3\2\2\2]`\3\2\2\2^\\\3\2\2"+
		"\2^_\3\2\2\2_a\3\2\2\2`^\3\2\2\2ab\7\13\2\2bc\b\7\1\2c\r\3\2\2\2de\7\n"+
		"\2\2ef\5\20\t\2fg\7\b\2\2gh\5\20\t\2hi\7\b\2\2ij\5\20\t\2jk\7\13\2\2k"+
		"l\b\b\1\2l\17\3\2\2\2mo\t\2\2\2nm\3\2\2\2no\3\2\2\2op\3\2\2\2ps\7\22\2"+
		"\2qr\7\16\2\2rt\7\22\2\2sq\3\2\2\2st\3\2\2\2tz\3\2\2\2uw\t\3\2\2vx\t\2"+
		"\2\2wv\3\2\2\2wx\3\2\2\2xy\3\2\2\2y{\7\22\2\2zu\3\2\2\2z{\3\2\2\2{|\3"+
		"\2\2\2|}\b\t\1\2}\21\3\2\2\2~\u0080\t\2\2\2\177~\3\2\2\2\177\u0080\3\2"+
		"\2\2\u0080\u0081\3\2\2\2\u0081\u0082\7\22\2\2\u0082\u0083\b\n\1\2\u0083"+
		"\23\3\2\2\2\f\33,>M^nswz\177";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}