package kufar.integration;

import kufar.KufarPoller;
import kufar.service.PropertyService;
import kufar.TestContext;
import kufar.RuleParams;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class KufarPollerTest {

  private KufarPoller kufarPoller;
  private PropertyService mockPropertyService;

  @Before
  public void setup() {
    kufarPoller = new KufarPoller();
    mockPropertyService = mock(PropertyService.class);
    kufarPoller.getEmailService().setPropertyService(mockPropertyService);
  }

  @Test
  public void test() {
    kufarPoller.handleRequest(RuleParams.builder().url("1").build(), new TestContext());
  }
}
