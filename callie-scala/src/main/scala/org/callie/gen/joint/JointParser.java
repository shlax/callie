// Generated from /home/pochodnicky/wrksp/no-git/callie/callie-scala/src/g4/Joint.g4 by ANTLR 4.10.1
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
	static { RuntimeMetaData.checkVersion("4.10.1", RuntimeMetaData.VERSION); }

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
		"\u0004\u0001\u0011\u0083\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001"+
		"\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004"+
		"\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007"+
		"\u0002\b\u0007\b\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001"+
		"\u0000\u0001\u0000\u0001\u0000\u0003\u0000\u001a\b\u0000\u0001\u0000\u0001"+
		"\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0002\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0005"+
		"\u0002)\b\u0002\n\u0002\f\u0002,\t\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0005\u0003;\b"+
		"\u0003\n\u0003\f\u0003>\t\u0003\u0001\u0003\u0001\u0003\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0005\u0004J\b\u0004\n\u0004\f\u0004M\t\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0005\u0005[\b"+
		"\u0005\n\u0005\f\u0005^\t\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0007\u0003\u0007m\b\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0003\u0007r\b\u0007\u0001\u0007\u0001"+
		"\u0007\u0003\u0007v\b\u0007\u0001\u0007\u0003\u0007y\b\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\b\u0003\b~\b\b\u0001\b\u0001\b\u0001\b\u0001\b\u0000"+
		"\u0000\t\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0000\u0002\u0001\u0000"+
		"\n\u000b\u0001\u0000\r\u000e\u0083\u0000\u0012\u0001\u0000\u0000\u0000"+
		"\u0002\u001d\u0001\u0000\u0000\u0000\u0004!\u0001\u0000\u0000\u0000\u0006"+
		"1\u0001\u0000\u0000\u0000\bA\u0001\u0000\u0000\u0000\nP\u0001\u0000\u0000"+
		"\u0000\fb\u0001\u0000\u0000\u0000\u000el\u0001\u0000\u0000\u0000\u0010"+
		"}\u0001\u0000\u0000\u0000\u0012\u0019\u0005\u0001\u0000\u0000\u0013\u0014"+
		"\u0003\u0004\u0002\u0000\u0014\u0015\u0006\u0000\uffff\uffff\u0000\u0015"+
		"\u001a\u0001\u0000\u0000\u0000\u0016\u0017\u0003\u0006\u0003\u0000\u0017"+
		"\u0018\u0006\u0000\uffff\uffff\u0000\u0018\u001a\u0001\u0000\u0000\u0000"+
		"\u0019\u0013\u0001\u0000\u0000\u0000\u0019\u0016\u0001\u0000\u0000\u0000"+
		"\u001a\u001b\u0001\u0000\u0000\u0000\u001b\u001c\u0005\u0002\u0000\u0000"+
		"\u001c\u0001\u0001\u0000\u0000\u0000\u001d\u001e\u0005\u0003\u0000\u0000"+
		"\u001e\u001f\u0005\u000f\u0000\u0000\u001f \u0006\u0001\uffff\uffff\u0000"+
		" \u0003\u0001\u0000\u0000\u0000!\"\u0006\u0002\uffff\uffff\u0000\"#\u0005"+
		"\u000f\u0000\u0000#$\u0005\u0003\u0000\u0000$*\u0003\f\u0006\u0000%&\u0003"+
		"\u0002\u0001\u0000&\'\u0006\u0002\uffff\uffff\u0000\')\u0001\u0000\u0000"+
		"\u0000(%\u0001\u0000\u0000\u0000),\u0001\u0000\u0000\u0000*(\u0001\u0000"+
		"\u0000\u0000*+\u0001\u0000\u0000\u0000+-\u0001\u0000\u0000\u0000,*\u0001"+
		"\u0000\u0000\u0000-.\u0005\u0004\u0000\u0000./\u0003\b\u0004\u0000/0\u0006"+
		"\u0002\uffff\uffff\u00000\u0005\u0001\u0000\u0000\u000012\u0006\u0003"+
		"\uffff\uffff\u000023\u0005\u000f\u0000\u000034\u0005\u0004\u0000\u0000"+
		"45\u0003\f\u0006\u000056\u0005\u0004\u0000\u00006<\u0003\b\u0004\u0000"+
		"78\u0003\u0000\u0000\u000089\u0006\u0003\uffff\uffff\u00009;\u0001\u0000"+
		"\u0000\u0000:7\u0001\u0000\u0000\u0000;>\u0001\u0000\u0000\u0000<:\u0001"+
		"\u0000\u0000\u0000<=\u0001\u0000\u0000\u0000=?\u0001\u0000\u0000\u0000"+
		"><\u0001\u0000\u0000\u0000?@\u0006\u0003\uffff\uffff\u0000@\u0007\u0001"+
		"\u0000\u0000\u0000AB\u0006\u0004\uffff\uffff\u0000BC\u0005\u0005\u0000"+
		"\u0000CD\u0003\n\u0005\u0000DK\u0006\u0004\uffff\uffff\u0000EF\u0005\u0006"+
		"\u0000\u0000FG\u0003\n\u0005\u0000GH\u0006\u0004\uffff\uffff\u0000HJ\u0001"+
		"\u0000\u0000\u0000IE\u0001\u0000\u0000\u0000JM\u0001\u0000\u0000\u0000"+
		"KI\u0001\u0000\u0000\u0000KL\u0001\u0000\u0000\u0000LN\u0001\u0000\u0000"+
		"\u0000MK\u0001\u0000\u0000\u0000NO\u0005\u0007\u0000\u0000O\t\u0001\u0000"+
		"\u0000\u0000PQ\u0006\u0005\uffff\uffff\u0000QR\u0005\u000f\u0000\u0000"+
		"RS\u0005\u0004\u0000\u0000ST\u0005\b\u0000\u0000TU\u0003\u0010\b\u0000"+
		"U\\\u0006\u0005\uffff\uffff\u0000VW\u0005\u0006\u0000\u0000WX\u0003\u0010"+
		"\b\u0000XY\u0006\u0005\uffff\uffff\u0000Y[\u0001\u0000\u0000\u0000ZV\u0001"+
		"\u0000\u0000\u0000[^\u0001\u0000\u0000\u0000\\Z\u0001\u0000\u0000\u0000"+
		"\\]\u0001\u0000\u0000\u0000]_\u0001\u0000\u0000\u0000^\\\u0001\u0000\u0000"+
		"\u0000_`\u0005\t\u0000\u0000`a\u0006\u0005\uffff\uffff\u0000a\u000b\u0001"+
		"\u0000\u0000\u0000bc\u0005\b\u0000\u0000cd\u0003\u000e\u0007\u0000de\u0005"+
		"\u0006\u0000\u0000ef\u0003\u000e\u0007\u0000fg\u0005\u0006\u0000\u0000"+
		"gh\u0003\u000e\u0007\u0000hi\u0005\t\u0000\u0000ij\u0006\u0006\uffff\uffff"+
		"\u0000j\r\u0001\u0000\u0000\u0000km\u0007\u0000\u0000\u0000lk\u0001\u0000"+
		"\u0000\u0000lm\u0001\u0000\u0000\u0000mn\u0001\u0000\u0000\u0000nq\u0005"+
		"\u0010\u0000\u0000op\u0005\f\u0000\u0000pr\u0005\u0010\u0000\u0000qo\u0001"+
		"\u0000\u0000\u0000qr\u0001\u0000\u0000\u0000rx\u0001\u0000\u0000\u0000"+
		"su\u0007\u0001\u0000\u0000tv\u0007\u0000\u0000\u0000ut\u0001\u0000\u0000"+
		"\u0000uv\u0001\u0000\u0000\u0000vw\u0001\u0000\u0000\u0000wy\u0005\u0010"+
		"\u0000\u0000xs\u0001\u0000\u0000\u0000xy\u0001\u0000\u0000\u0000yz\u0001"+
		"\u0000\u0000\u0000z{\u0006\u0007\uffff\uffff\u0000{\u000f\u0001\u0000"+
		"\u0000\u0000|~\u0007\u0000\u0000\u0000}|\u0001\u0000\u0000\u0000}~\u0001"+
		"\u0000\u0000\u0000~\u007f\u0001\u0000\u0000\u0000\u007f\u0080\u0005\u0010"+
		"\u0000\u0000\u0080\u0081\u0006\b\uffff\uffff\u0000\u0081\u0011\u0001\u0000"+
		"\u0000\u0000\n\u0019*<K\\lqux}";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}