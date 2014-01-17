package org.angularjs.codeInsight;

import com.intellij.lang.javascript.psi.JSVariable;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixtureTestCase;
import org.angularjs.AngularTestUtil;

/**
 * @author Dennis.Ushakov
 */
public class InjectionsTest extends LightPlatformCodeInsightFixtureTestCase {
  @Override
  protected String getTestDataPath() {
    return AngularTestUtil.getBaseTestDataPath(getClass()) + "injections";
  }

  @Override
  protected boolean isWriteActionRequired() {
    return getTestName(true).contains("Completion");
  }

  public void testNgInitCompletion() {
    myFixture.testCompletion("ngInit.html", "ngInit.after.html", "angular.js");
  }

  public void testNgInitResolve() {
    myFixture.configureByFiles("ngInit.resolve.html", "angular.js");
    int offsetBySignature = AngularTestUtil.findOffsetBySignature("fri<caret>ends", myFixture.getFile());
    PsiReference ref = myFixture.getFile().findReferenceAt(offsetBySignature);
    assertNotNull(ref);
    PsiElement resolve = ref.resolve();
    assertInstanceOf(resolve, JSVariable.class);
    assertEquals("friends", ((JSVariable)resolve).getName());
  }

  public void testNgRepeatImplicitCompletion() {
    myFixture.configureByFiles("ngRepeatImplicit.html", "angular.js");
    myFixture.testCompletionVariants("ngRepeatImplicit.html", "$index", "$first", "$middle", "$last", "$even", "$odd");
  }

  public void testNgRepeatImplicitResolve() {
    myFixture.configureByFiles("ngRepeatImplicitType.html", "angular.js");
    int offsetBySignature = AngularTestUtil.findOffsetBySignature("ind<caret>ex", myFixture.getFile());
    PsiReference ref = myFixture.getFile().findReferenceAt(offsetBySignature);
    assertNotNull(ref);
    PsiElement resolve = ref.resolve();
    assertInstanceOf(resolve, JSVariable.class);
    assertEquals("$index", ((JSVariable)resolve).getName());
    assertEquals("Number", ((JSVariable)resolve).getTypeString());
  }
}
