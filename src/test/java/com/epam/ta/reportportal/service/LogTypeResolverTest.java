package com.epam.ta.reportportal.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.epam.ta.reportportal.dao.LogTypeRepository;
import com.epam.ta.reportportal.entity.enums.LogLevel;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LogTypeResolverTest {

  @Mock
  private LogTypeRepository logTypeRepository;

  @InjectMocks
  private LogTypeResolver logTypeResolver;

  @Test
  void resolveLevelWhenLevelNameIsNullShouldReturnUnknownLevel() {
    // given
    Long projectId = 1L;
    String levelName = null;

    // when
    int result = logTypeResolver.resolveLogLevelFromName(projectId, levelName);

    // then
    assertEquals(LogLevel.UNKNOWN_INT, result);
    verifyNoInteractions(logTypeRepository);
  }

  @Test
  void resolveLevelWhenLevelNameIsEmptyShouldReturnUnknownLevel() {
    // given
    Long projectId = 1L;
    String levelName = "";

    // when
    int result = logTypeResolver.resolveLogLevelFromName(projectId, levelName);

    // then
    assertEquals(LogLevel.UNKNOWN_INT, result);
    verifyNoInteractions(logTypeRepository);
  }

  @Test
  void resolveLevelWhenLevelNameMatchesLogLevelEnumShouldReturnMatchingLevel() {
    // given
    Long projectId = 1L;
    String levelName = "INFO";

    // when
    int result = logTypeResolver.resolveLogLevelFromName(projectId, levelName);

    // then
    assertEquals(LogLevel.INFO_INT, result);
    verifyNoInteractions(logTypeRepository);
  }

  @Test
  void resolveLevelWhenNameDoesNotMatchEnumButExistsInDbShouldReturnDbValue() {
    // given
    Long projectId = 1L;
    String levelName = "CUSTOM_LEVEL";

    int expectedDbValue = 25000;
    when(logTypeRepository.findLevelByProjectIdAndNameIgnoreCase(projectId, levelName))
        .thenReturn(Optional.of(expectedDbValue));

    // when
    int result = logTypeResolver.resolveLogLevelFromName(projectId, levelName);

    // then
    assertEquals(expectedDbValue, result);
    verify(logTypeRepository).findLevelByProjectIdAndNameIgnoreCase(projectId, levelName);
  }

  @Test
  void resolveLevelWhenNameDoesNotMatchEnumAndDbHasNoEntryShouldReturnUnknownLevel() {
    // given
    Long projectId = 1L;
    String levelName = "NON_EXISTENT_LEVEL";

    when(logTypeRepository.findLevelByProjectIdAndNameIgnoreCase(projectId, levelName))
        .thenReturn(Optional.empty());

    // when
    int result = logTypeResolver.resolveLogLevelFromName(projectId, levelName);

    // then
    assertEquals(LogLevel.UNKNOWN_INT, result);
    verify(logTypeRepository).findLevelByProjectIdAndNameIgnoreCase(projectId, levelName);
  }

  @Test
  void resolveNameFromLogLevelWhenLogLevelMatchesEnumShouldReturnCorrectLevelName() {
    // given
    Long projectId = 1L;
    int logLevel = 40000;

    // when
    String result = logTypeResolver.resolveNameFromLogLevel(projectId, logLevel);

    // then
    assertEquals("ERROR", result);
    verifyNoInteractions(logTypeRepository);
  }

  @Test
  void resolveNameFromLogLevelWhenLogLevelMatchesCustomLevelShouldReturnName() {
    // given
    Long projectId = 1L;
    int logLevel = 35000;
    when(logTypeRepository.findNameByProjectIdAndLevel(projectId, logLevel))
        .thenReturn("CUSTOM_ERROR");

    // when
    String result = logTypeResolver.resolveNameFromLogLevel(projectId, logLevel);

    // then
    assertEquals("CUSTOM_ERROR", result);
    verify(logTypeRepository).findNameByProjectIdAndLevel(projectId, logLevel);
  }

  @Test
  void resolveNameFromLogLevelWhenLogLevelDoesNotMatchEnumAndDbHasNoEntryShouldReturnUnknown() {
    // given
    Long projectId = 1L;
    int logLevel = 99999;
    when(logTypeRepository.findNameByProjectIdAndLevel(projectId, logLevel))
        .thenReturn(null);

    // when
    String result = logTypeResolver.resolveNameFromLogLevel(projectId, logLevel);

    // then
    assertEquals("UNKNOWN", result);
    verify(logTypeRepository).findNameByProjectIdAndLevel(projectId, logLevel);
  }

}