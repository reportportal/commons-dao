/*
 * Copyright 2019 EPAM Systems
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.epam.ta.reportportal.entity;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

public class LTreeType implements UserType<LTreeType>, Serializable {

  @Override
  public int getSqlType() {
    return Types.OTHER;
  }

  @SuppressWarnings("rawtypes")
  @Override
  public Class<LTreeType> returnedClass() {
    return LTreeType.class;
  }

  @Override
  public boolean equals(LTreeType x, LTreeType y) {
    return x == y;
  }

  @Override
  public int hashCode(LTreeType x) {
    return x.hashCode();
  }

  @Override
  public LTreeType nullSafeGet(ResultSet rs, int position, SharedSessionContractImplementor session,
      Object owner) throws SQLException {
    return (LTreeType) rs.getObject(position);
  }

  @Override
  public void nullSafeSet(PreparedStatement st, LTreeType value, int index,
      SharedSessionContractImplementor session) throws SQLException {
    st.setObject(index, value, Types.OTHER);

  }

  @Override
  public LTreeType deepCopy(LTreeType value) {
    return value;
  }

  @Override
  public boolean isMutable() {
    return false;
  }

  @Override
  public Serializable disassemble(LTreeType value) {
    return value;
  }

  @Override
  public LTreeType assemble(Serializable cached, Object owner) {
    return deepCopy((LTreeType) cached);
  }

}
