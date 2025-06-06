package com.braintribe.templatetools;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import com.braintribe.utils.template.Template;
import com.braintribe.utils.template.model.Sequence;
import com.braintribe.utils.template.model.StaticText;
import com.braintribe.utils.template.model.TemplateNode;
import com.braintribe.utils.template.model.Variable;

public class TemplateTest {

	@Test
	public void testTemplates() {
		check("static only", new Txt("static only"));
		check("static ${one}", new Txt("static "), new Var("one"));
		check("static $${escaped} $${anotherescape} static", new Txt("static ${escaped} ${anotherescape} static"));
		check("static ${var} $${anotherescape} static", new Txt("static "), new Var("var"), new Txt(" ${anotherescape} static"));
	}
	
	private class Expectation {
		public boolean var;
		public String text;
		public Expectation(boolean var, String text) {
			super();
			this.var = var;
			this.text = text;
		}
	}
	
	private class Var extends Expectation {
		public Var(String name) {
			super(true, name);
		}
	}
	
	private class Txt extends Expectation {
		public Txt(String text)  {
			super(false, text);
		}
	}
	
	private void check(String expression, Expectation... expectations) {
		Template template = Template.parse(expression);
		
		List<TemplateNode> nodes = new ArrayList<TemplateNode>();
		
		template.getRootNode().walk(node -> {
			if (!(node instanceof Sequence))
				nodes.add(node);
			return true;
		});
		
		if (expectations.length != nodes.size())
			Assertions.fail("unexpected size of template nodes");
		
		for (int i = 0; i < expectations.length; i++) {
			Expectation ex = expectations[i];
			TemplateNode node = nodes.get(i);
			
			if (ex.var) { 
				Assertions.assertThat(node instanceof Variable);
				Variable var = (Variable)node;
				Assertions.assertThat(var.getVariableName()).isEqualTo(ex.text);
			}
			else {
				Assertions.assertThat(node instanceof StaticText);
				StaticText txt = (StaticText)node;
				Assertions.assertThat(txt.getText()).isEqualTo(ex.text);
			}
		}
	}
}
