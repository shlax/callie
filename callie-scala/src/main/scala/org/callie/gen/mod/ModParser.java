// Generated from /home/pochodnicky/wrksp/no-git/callie/callie-scala/src/g4/Mod.g4 by ANTLR 4.10.1
package org.callie.gen.mod;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ModParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.10.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, DIGITS=14, WS=15;
	public static final int
		RULE_mod = 0, RULE_face3 = 1, RULE_face = 2, RULE_uvs = 3, RULE_points = 4, 
		RULE_vector3 = 5, RULE_vector2 = 6, RULE_floatNum = 7, RULE_intNum = 8;
	private static String[] makeRuleNames() {
		return new String[] {
			"mod", "face3", "face", "uvs", "points", "vector3", "vector2", "floatNum", 
			"intNum"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'{'", "'}'", "'['", "','", "']'", "':'", "'('", "')'", "'+'", 
			"'-'", "'.'", "'e'", "'E'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, "DIGITS", "WS"
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
	public String getGrammarFileName() { return "Mod.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public ModParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class ModContext extends ParserRuleContext {
		public org.callie.model.Model result;
		public PointsContext p;
		public Face3Context f;
		public Face3Context fi;
		public PointsContext points() {
			return getRuleContext(PointsContext.class,0);
		}
		public List<Face3Context> face3() {
			return getRuleContexts(Face3Context.class);
		}
		public Face3Context face3(int i) {
			return getRuleContext(Face3Context.class,i);
		}
		public ModContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mod; }
	}

	public final ModContext mod() throws RecognitionException {
		ModContext _localctx = new ModContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_mod);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			 java.util.List<org.callie.model.Face> lf = new java.util.ArrayList<>(); 
			setState(19);
			((ModContext)_localctx).p = points();
			setState(20);
			match(T__0);
			setState(21);
			((ModContext)_localctx).f = face3();
			 lf.addAll(((ModContext)_localctx).f.r); 
			setState(28);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__2) {
				{
				{
				setState(23);
				((ModContext)_localctx).fi = face3();
				 lf.addAll(((ModContext)_localctx).fi.r); 
				}
				}
				setState(30);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(31);
			match(T__1);
			 ((ModContext)_localctx).result =  new org.callie.model.Model( ((ModContext)_localctx).p.r.toArray(new org.callie.model.Point3[0] ), lf.toArray( new org.callie.model.Face[0] ) ); 
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

	public static class Face3Context extends ParserRuleContext {
		public java.util.List<org.callie.model.Face> r;
		public FaceContext a;
		public FaceContext b;
		public FaceContext c;
		public List<FaceContext> face() {
			return getRuleContexts(FaceContext.class);
		}
		public FaceContext face(int i) {
			return getRuleContext(FaceContext.class,i);
		}
		public Face3Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_face3; }
	}

	public final Face3Context face3() throws RecognitionException {
		Face3Context _localctx = new Face3Context(_ctx, getState());
		enterRule(_localctx, 2, RULE_face3);
		try {
			enterOuterAlt(_localctx, 1);
			{
			 ((Face3Context)_localctx).r =  new java.util.ArrayList<>(3); 
			setState(35);
			match(T__2);
			setState(36);
			((Face3Context)_localctx).a = face();
			setState(37);
			match(T__3);
			setState(38);
			((Face3Context)_localctx).b = face();
			setState(39);
			match(T__3);
			setState(40);
			((Face3Context)_localctx).c = face();
			setState(41);
			match(T__4);
			 _localctx.r.add(((Face3Context)_localctx).a.r); _localctx.r.add(((Face3Context)_localctx).b.r); _localctx.r.add(((Face3Context)_localctx).c.r); 
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

	public static class FaceContext extends ParserRuleContext {
		public org.callie.model.Face r;
		public IntNumContext i;
		public Vector3Context n;
		public UvsContext u;
		public IntNumContext intNum() {
			return getRuleContext(IntNumContext.class,0);
		}
		public Vector3Context vector3() {
			return getRuleContext(Vector3Context.class,0);
		}
		public UvsContext uvs() {
			return getRuleContext(UvsContext.class,0);
		}
		public FaceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_face; }
	}

	public final FaceContext face() throws RecognitionException {
		FaceContext _localctx = new FaceContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_face);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(44);
			((FaceContext)_localctx).i = intNum();
			setState(45);
			match(T__5);
			setState(46);
			((FaceContext)_localctx).n = vector3();
			setState(47);
			match(T__5);
			setState(48);
			((FaceContext)_localctx).u = uvs();
			 ((FaceContext)_localctx).r =  new org.callie.model.Face(((FaceContext)_localctx).i.r, ((FaceContext)_localctx).n.r, ((FaceContext)_localctx).u.r.toArray( new org.callie.model.Point2[0] ) ); 
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

	public static class UvsContext extends ParserRuleContext {
		public java.util.List<org.callie.model.Point2> r;
		public Vector2Context p;
		public Vector2Context pi;
		public List<Vector2Context> vector2() {
			return getRuleContexts(Vector2Context.class);
		}
		public Vector2Context vector2(int i) {
			return getRuleContext(Vector2Context.class,i);
		}
		public UvsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_uvs; }
	}

	public final UvsContext uvs() throws RecognitionException {
		UvsContext _localctx = new UvsContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_uvs);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			 ((UvsContext)_localctx).r =  new java.util.ArrayList<>(); 
			setState(52);
			match(T__2);
			setState(53);
			((UvsContext)_localctx).p = vector2();
			 _localctx.r.add(((UvsContext)_localctx).p.r); 
			setState(61);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__3) {
				{
				{
				setState(55);
				match(T__3);
				setState(56);
				((UvsContext)_localctx).pi = vector2();
				 _localctx.r.add(((UvsContext)_localctx).pi.r); 
				}
				}
				setState(63);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(64);
			match(T__4);
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

	public static class PointsContext extends ParserRuleContext {
		public java.util.List<org.callie.model.Point3> r;
		public Vector3Context p;
		public Vector3Context pi;
		public List<Vector3Context> vector3() {
			return getRuleContexts(Vector3Context.class);
		}
		public Vector3Context vector3(int i) {
			return getRuleContext(Vector3Context.class,i);
		}
		public PointsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_points; }
	}

	public final PointsContext points() throws RecognitionException {
		PointsContext _localctx = new PointsContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_points);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			 ((PointsContext)_localctx).r =  new java.util.ArrayList<>(); 
			setState(67);
			match(T__2);
			setState(68);
			((PointsContext)_localctx).p = vector3();
			 _localctx.r.add(((PointsContext)_localctx).p.r); 
			setState(76);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__3) {
				{
				{
				setState(70);
				match(T__3);
				setState(71);
				((PointsContext)_localctx).pi = vector3();
				 _localctx.r.add(((PointsContext)_localctx).pi.r); 
				}
				}
				setState(78);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(79);
			match(T__4);
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

	public static class Vector3Context extends ParserRuleContext {
		public org.callie.model.Point3 r;
		public FloatNumContext a;
		public FloatNumContext b;
		public FloatNumContext c;
		public List<FloatNumContext> floatNum() {
			return getRuleContexts(FloatNumContext.class);
		}
		public FloatNumContext floatNum(int i) {
			return getRuleContext(FloatNumContext.class,i);
		}
		public Vector3Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_vector3; }
	}

	public final Vector3Context vector3() throws RecognitionException {
		Vector3Context _localctx = new Vector3Context(_ctx, getState());
		enterRule(_localctx, 10, RULE_vector3);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(81);
			match(T__6);
			setState(82);
			((Vector3Context)_localctx).a = floatNum();
			setState(83);
			match(T__3);
			setState(84);
			((Vector3Context)_localctx).b = floatNum();
			setState(85);
			match(T__3);
			setState(86);
			((Vector3Context)_localctx).c = floatNum();
			setState(87);
			match(T__7);
			 ((Vector3Context)_localctx).r =  new org.callie.model.Point3(((Vector3Context)_localctx).a.r, ((Vector3Context)_localctx).b.r, ((Vector3Context)_localctx).c.r); 
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

	public static class Vector2Context extends ParserRuleContext {
		public org.callie.model.Point2 r;
		public FloatNumContext a;
		public FloatNumContext b;
		public List<FloatNumContext> floatNum() {
			return getRuleContexts(FloatNumContext.class);
		}
		public FloatNumContext floatNum(int i) {
			return getRuleContext(FloatNumContext.class,i);
		}
		public Vector2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_vector2; }
	}

	public final Vector2Context vector2() throws RecognitionException {
		Vector2Context _localctx = new Vector2Context(_ctx, getState());
		enterRule(_localctx, 12, RULE_vector2);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(90);
			match(T__6);
			setState(91);
			((Vector2Context)_localctx).a = floatNum();
			setState(92);
			match(T__3);
			setState(93);
			((Vector2Context)_localctx).b = floatNum();
			setState(94);
			match(T__7);
			 ((Vector2Context)_localctx).r =  new org.callie.model.Point2(((Vector2Context)_localctx).a.r, ((Vector2Context)_localctx).b.r); 
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
		public List<TerminalNode> DIGITS() { return getTokens(ModParser.DIGITS); }
		public TerminalNode DIGITS(int i) {
			return getToken(ModParser.DIGITS, i);
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
			setState(98);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__8 || _la==T__9) {
				{
				setState(97);
				((FloatNumContext)_localctx).s = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==T__8 || _la==T__9) ) {
					((FloatNumContext)_localctx).s = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
			}

			setState(100);
			((FloatNumContext)_localctx).n = match(DIGITS);
			setState(103);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__10) {
				{
				setState(101);
				match(T__10);
				setState(102);
				((FloatNumContext)_localctx).m = match(DIGITS);
				}
			}

			setState(110);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__11 || _la==T__12) {
				{
				setState(105);
				_la = _input.LA(1);
				if ( !(_la==T__11 || _la==T__12) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(107);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__8 || _la==T__9) {
					{
					setState(106);
					((FloatNumContext)_localctx).e = _input.LT(1);
					_la = _input.LA(1);
					if ( !(_la==T__8 || _la==T__9) ) {
						((FloatNumContext)_localctx).e = (Token)_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
				}

				setState(109);
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
		public TerminalNode DIGITS() { return getToken(ModParser.DIGITS, 0); }
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
			setState(115);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__8 || _la==T__9) {
				{
				setState(114);
				((IntNumContext)_localctx).s = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==T__8 || _la==T__9) ) {
					((IntNumContext)_localctx).s = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
			}

			setState(117);
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
		"\u0004\u0001\u000fy\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0000\u0001\u0000\u0005\u0000\u001b\b\u0000\n\u0000"+
		"\f\u0000\u001e\t\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0005\u0003<\b\u0003\n\u0003\f\u0003?\t\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0005\u0004K\b\u0004\n\u0004\f\u0004N\t\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0007\u0003\u0007c\b\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0003\u0007h\b\u0007\u0001\u0007\u0001\u0007\u0003\u0007l\b\u0007\u0001"+
		"\u0007\u0003\u0007o\b\u0007\u0001\u0007\u0001\u0007\u0001\b\u0003\bt\b"+
		"\b\u0001\b\u0001\b\u0001\b\u0001\b\u0000\u0000\t\u0000\u0002\u0004\u0006"+
		"\b\n\f\u000e\u0010\u0000\u0002\u0001\u0000\t\n\u0001\u0000\f\rw\u0000"+
		"\u0012\u0001\u0000\u0000\u0000\u0002\"\u0001\u0000\u0000\u0000\u0004,"+
		"\u0001\u0000\u0000\u0000\u00063\u0001\u0000\u0000\u0000\bB\u0001\u0000"+
		"\u0000\u0000\nQ\u0001\u0000\u0000\u0000\fZ\u0001\u0000\u0000\u0000\u000e"+
		"b\u0001\u0000\u0000\u0000\u0010s\u0001\u0000\u0000\u0000\u0012\u0013\u0006"+
		"\u0000\uffff\uffff\u0000\u0013\u0014\u0003\b\u0004\u0000\u0014\u0015\u0005"+
		"\u0001\u0000\u0000\u0015\u0016\u0003\u0002\u0001\u0000\u0016\u001c\u0006"+
		"\u0000\uffff\uffff\u0000\u0017\u0018\u0003\u0002\u0001\u0000\u0018\u0019"+
		"\u0006\u0000\uffff\uffff\u0000\u0019\u001b\u0001\u0000\u0000\u0000\u001a"+
		"\u0017\u0001\u0000\u0000\u0000\u001b\u001e\u0001\u0000\u0000\u0000\u001c"+
		"\u001a\u0001\u0000\u0000\u0000\u001c\u001d\u0001\u0000\u0000\u0000\u001d"+
		"\u001f\u0001\u0000\u0000\u0000\u001e\u001c\u0001\u0000\u0000\u0000\u001f"+
		" \u0005\u0002\u0000\u0000 !\u0006\u0000\uffff\uffff\u0000!\u0001\u0001"+
		"\u0000\u0000\u0000\"#\u0006\u0001\uffff\uffff\u0000#$\u0005\u0003\u0000"+
		"\u0000$%\u0003\u0004\u0002\u0000%&\u0005\u0004\u0000\u0000&\'\u0003\u0004"+
		"\u0002\u0000\'(\u0005\u0004\u0000\u0000()\u0003\u0004\u0002\u0000)*\u0005"+
		"\u0005\u0000\u0000*+\u0006\u0001\uffff\uffff\u0000+\u0003\u0001\u0000"+
		"\u0000\u0000,-\u0003\u0010\b\u0000-.\u0005\u0006\u0000\u0000./\u0003\n"+
		"\u0005\u0000/0\u0005\u0006\u0000\u000001\u0003\u0006\u0003\u000012\u0006"+
		"\u0002\uffff\uffff\u00002\u0005\u0001\u0000\u0000\u000034\u0006\u0003"+
		"\uffff\uffff\u000045\u0005\u0003\u0000\u000056\u0003\f\u0006\u00006=\u0006"+
		"\u0003\uffff\uffff\u000078\u0005\u0004\u0000\u000089\u0003\f\u0006\u0000"+
		"9:\u0006\u0003\uffff\uffff\u0000:<\u0001\u0000\u0000\u0000;7\u0001\u0000"+
		"\u0000\u0000<?\u0001\u0000\u0000\u0000=;\u0001\u0000\u0000\u0000=>\u0001"+
		"\u0000\u0000\u0000>@\u0001\u0000\u0000\u0000?=\u0001\u0000\u0000\u0000"+
		"@A\u0005\u0005\u0000\u0000A\u0007\u0001\u0000\u0000\u0000BC\u0006\u0004"+
		"\uffff\uffff\u0000CD\u0005\u0003\u0000\u0000DE\u0003\n\u0005\u0000EL\u0006"+
		"\u0004\uffff\uffff\u0000FG\u0005\u0004\u0000\u0000GH\u0003\n\u0005\u0000"+
		"HI\u0006\u0004\uffff\uffff\u0000IK\u0001\u0000\u0000\u0000JF\u0001\u0000"+
		"\u0000\u0000KN\u0001\u0000\u0000\u0000LJ\u0001\u0000\u0000\u0000LM\u0001"+
		"\u0000\u0000\u0000MO\u0001\u0000\u0000\u0000NL\u0001\u0000\u0000\u0000"+
		"OP\u0005\u0005\u0000\u0000P\t\u0001\u0000\u0000\u0000QR\u0005\u0007\u0000"+
		"\u0000RS\u0003\u000e\u0007\u0000ST\u0005\u0004\u0000\u0000TU\u0003\u000e"+
		"\u0007\u0000UV\u0005\u0004\u0000\u0000VW\u0003\u000e\u0007\u0000WX\u0005"+
		"\b\u0000\u0000XY\u0006\u0005\uffff\uffff\u0000Y\u000b\u0001\u0000\u0000"+
		"\u0000Z[\u0005\u0007\u0000\u0000[\\\u0003\u000e\u0007\u0000\\]\u0005\u0004"+
		"\u0000\u0000]^\u0003\u000e\u0007\u0000^_\u0005\b\u0000\u0000_`\u0006\u0006"+
		"\uffff\uffff\u0000`\r\u0001\u0000\u0000\u0000ac\u0007\u0000\u0000\u0000"+
		"ba\u0001\u0000\u0000\u0000bc\u0001\u0000\u0000\u0000cd\u0001\u0000\u0000"+
		"\u0000dg\u0005\u000e\u0000\u0000ef\u0005\u000b\u0000\u0000fh\u0005\u000e"+
		"\u0000\u0000ge\u0001\u0000\u0000\u0000gh\u0001\u0000\u0000\u0000hn\u0001"+
		"\u0000\u0000\u0000ik\u0007\u0001\u0000\u0000jl\u0007\u0000\u0000\u0000"+
		"kj\u0001\u0000\u0000\u0000kl\u0001\u0000\u0000\u0000lm\u0001\u0000\u0000"+
		"\u0000mo\u0005\u000e\u0000\u0000ni\u0001\u0000\u0000\u0000no\u0001\u0000"+
		"\u0000\u0000op\u0001\u0000\u0000\u0000pq\u0006\u0007\uffff\uffff\u0000"+
		"q\u000f\u0001\u0000\u0000\u0000rt\u0007\u0000\u0000\u0000sr\u0001\u0000"+
		"\u0000\u0000st\u0001\u0000\u0000\u0000tu\u0001\u0000\u0000\u0000uv\u0005"+
		"\u000e\u0000\u0000vw\u0006\b\uffff\uffff\u0000w\u0011\u0001\u0000\u0000"+
		"\u0000\b\u001c=Lbgkns";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}