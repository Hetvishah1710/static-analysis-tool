package core.visitor.common;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.IPackageFragment;


import core.datastructure.impl.Method;

import core.helper.HelperClass;
import core.helper.ObjectCreationHelper;

public class EqualHashCodeBug extends ASTVisitor {

	private String fileName = "EqualhashCode.txt"; //Name of the output file that you wish to have
	private String sysName = "cloud-Stack"; //Name of the project
	private String className = "";
	private String methodName = "hashcode";
	private String mn = "equals";
	private CompilationUnit parsedunit;
	private HashMap<String,List<String>> hmap = new HashMap<String, List<String>>();
	private List<String> met;
	int count = 0;
	int count1 = 0;

	public EqualHashCodeBug(IPackageFragment packageFrag, ICompilationUnit unit, CompilationUnit parsedunit) {
		//System.out.println(packageFrag);
		//System.out.println(unit.getElementName());
		
		className = unit.getElementName().split("\\.")[0];
		
		//System.out.println(className);
		//met = new ArrayList<String>();
		this.parsedunit = parsedunit;

	}

	public boolean visit(MethodDeclaration method) {
		int i = 0;
		int j = 0;
		final Method callsite = ObjectCreationHelper.createMethodFromMethodDeclaration(method, className);
		java.lang.reflect.Method[] methods = parsedunit.getClass().getMethods();
		for (java.lang.reflect.Method method1 : methods) { 
			  
            String MethodName = method1.getName(); 
            if(MethodName.equalsIgnoreCase(methodName)) {
            	i = 1;
            }
            if(MethodName.equalsIgnoreCase(mn)) {
            	j = 1;
            }
        }
		if(i == 1 && j == 0) {
			String str = "<classname>" + className + "</classname>" + "defines hashcode but not equals";
			//System.out.println(str);
			try {
				HelperClass.fileAppendMethod(fileName, str);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			String str = "<classname>" + className + "</classname>" + " has both methods defined";
			try {
				HelperClass.fileAppendMethod(fileName, str);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	return true;
}
}

		

