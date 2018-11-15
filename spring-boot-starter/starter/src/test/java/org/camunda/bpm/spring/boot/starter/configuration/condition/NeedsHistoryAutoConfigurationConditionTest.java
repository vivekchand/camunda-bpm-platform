/*
 * Copyright © 2015 - 2018 camunda services GmbH and various authors (info@camunda.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.camunda.bpm.spring.boot.starter.configuration.condition;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;

public class NeedsHistoryAutoConfigurationConditionTest {

  @Test
  public void isHistoryAutoSupportedTest() {
    NeedsHistoryAutoConfigurationCondition condition = new NeedsHistoryAutoConfigurationCondition();
    assertFalse(condition.isHistoryAutoSupported());
    condition.historyAutoFieldName = "DB_SCHEMA_UPDATE_FALSE";
    assertFalse(condition.isHistoryAutoSupported());
  }

  @Test
  public void needsNoAdditionalConfigurationTest1() {
    NeedsHistoryAutoConfigurationCondition condition = spy(new NeedsHistoryAutoConfigurationCondition());
    ConditionContext context = mock(ConditionContext.class);
    Environment environment = mock(Environment.class);
    when(context.getEnvironment()).thenReturn(environment);
    assertFalse(condition.needsAdditionalConfiguration(context));
  }

  @Test
  public void needsNoAdditionalConfigurationTest2() {
    NeedsHistoryAutoConfigurationCondition condition = spy(new NeedsHistoryAutoConfigurationCondition());
    ConditionContext context = mock(ConditionContext.class);
    Environment environment = mock(Environment.class);
    when(context.getEnvironment()).thenReturn(environment);
    when(environment.getProperty("camunda.bpm.history-level")).thenReturn(NeedsHistoryAutoConfigurationCondition.HISTORY_AUTO);
    when(condition.isHistoryAutoSupported()).thenReturn(true);
    assertFalse(condition.needsAdditionalConfiguration(context));
  }

  @Test
  public void needsAdditionalConfigurationTest() {
    NeedsHistoryAutoConfigurationCondition condition = spy(new NeedsHistoryAutoConfigurationCondition());
    ConditionContext context = mock(ConditionContext.class);
    Environment environment = mock(Environment.class);
    when(context.getEnvironment()).thenReturn(environment);
    when(environment.getProperty("camunda.bpm.history-level")).thenReturn(NeedsHistoryAutoConfigurationCondition.HISTORY_AUTO);
    when(condition.isHistoryAutoSupported()).thenReturn(false);
    assertTrue(condition.needsAdditionalConfiguration(context));
  }

  @Test
  public void getMatchOutcomeMatchTest() {
    NeedsHistoryAutoConfigurationCondition condition = spy(new NeedsHistoryAutoConfigurationCondition());
    ConditionContext context = mock(ConditionContext.class);
    Environment environment = mock(Environment.class);
    when(context.getEnvironment()).thenReturn(environment);
    when(environment.getProperty("camunda.bpm.history-level")).thenReturn(NeedsHistoryAutoConfigurationCondition.HISTORY_AUTO);
    when(condition.needsAdditionalConfiguration(context)).thenReturn(true);
    assertTrue(condition.getMatchOutcome(context, null).isMatch());
  }

  @Test
  public void getMatchOutcomeNoMatchTest() {
    NeedsHistoryAutoConfigurationCondition condition = spy(new NeedsHistoryAutoConfigurationCondition());
    ConditionContext context = mock(ConditionContext.class);
    Environment environment = mock(Environment.class);
    when(context.getEnvironment()).thenReturn(environment);
    when(environment.getProperty("camunda.bpm.history-level")).thenReturn(NeedsHistoryAutoConfigurationCondition.HISTORY_AUTO);
    when(condition.needsAdditionalConfiguration(context)).thenReturn(false);
    assertFalse(condition.getMatchOutcome(context, null).isMatch());
  }

}
