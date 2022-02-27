package se.bjurr.gitchangelog.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.StringWriter;
import java.io.Writer;

import org.junit.Test;

import com.github.jknack.handlebars.Context;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;

public class TemplatesTest {

  public class Issue {
    private final boolean theVal;

    public Issue() {
      this.theVal = true;
    }

    public boolean isTheVal() {
      return this.theVal;
    }
  }

  @Test
  public void test() throws Exception {
    final Handlebars handlebars = new Handlebars();
    final Template template =
        handlebars.compileInline("{{isTheVal}}\n" + "isTheVal: {{#isTheVal}}yes{{/isTheVal}}\n");
    final Object context = new Issue();

    final Context changelogContext = Context.newContext(context);
    final Writer writer = new StringWriter();
    template.apply(changelogContext, writer);
    final String rendered = writer.toString();

    // Java 17
    // assertThat(rendered).isEqualTo("true\n" + "isTheVal: yes\n");
    // Java 11
    assertThat(rendered).isEqualTo("\n" + "isTheVal: \n");
  }
}
