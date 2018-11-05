/*
 *  Copyright (C) 2018 EPAM Systems
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.epam.ta.reportportal.entity.project.email;

import com.google.common.collect.Sets;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;
import java.util.function.Supplier;

/**
 * @author Pavel Bortnik
 */
@Entity
@Table(name = "sender_case")
public class SenderCase implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "key")
	private SendCaseType key;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "email_sender_case_id", nullable = false)
	private EmailSenderCase emailSenderCase;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "sender_case_value", joinColumns = @JoinColumn(name = "sender_case_id"))
	@Column(name = "value")
	private Set<String> values;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SendCaseType getKey() {
		return key;
	}

	public void setKey(SendCaseType key) {
		this.key = key;
	}

	public Set<String> getValues() {
		return values;
	}

	public void setValues(Set<String> values) {
		this.values = values;
	}

	public static class SenderCaseBuilder implements Supplier<SenderCase> {

		private SenderCase senderCase;

		public SenderCaseBuilder() {
			this.senderCase = new SenderCase();
		}

		public SenderCaseBuilder withKey(SendCaseType key) {
			senderCase.key = key;
			return this;
		}

		public SenderCaseBuilder withValue(String value) {
			senderCase.values = senderCase.values == null ? Sets.newHashSet() : senderCase.values;
			senderCase.values.add(value);
			return this;
		}

		public SenderCaseBuilder withValues(Set<String> values) {
			senderCase.values = values;
			return this;
		}

		@Override
		public SenderCase get() {
			return senderCase;
		}
	}
}
