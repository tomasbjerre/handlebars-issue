package se.bjurr.gitchangelog.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.StringWriter;
import java.io.Writer;

import org.junit.Test;

import com.github.jknack.handlebars.Context;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;

public class TemplatesTest {
  public enum SettingsIssueType {
    GITHUB
  }

  public class Issue {
    private final SettingsIssueType issueType;

    public Issue() {
      this.issueType = SettingsIssueType.GITHUB;
    }

    public boolean isGitHub() {
      return this.issueType == SettingsIssueType.GITHUB;
    }
  }

  @Test
  public void test() throws Exception {

    final Handlebars handlebars = new Handlebars();
    final Template template =
        handlebars.compileInline("{{isGitHub}}\n" + "isGitHub: {{#isGitHub}}yes{{/isGitHub}}\n");
    final Object context = new Issue();

    final Context changelogContext = Context.newContext(context);
    final Writer writer = new StringWriter();
    template.apply(changelogContext, writer);
    final String rendered = writer.toString();

    // Java 17
    assertThat(rendered).isEqualTo("true\n" + "isGitHub: yes\n");
    // Java 11
    // assertThat(rendered).isEqualTo("\n" + "isGitHub: \n");
  }
}
