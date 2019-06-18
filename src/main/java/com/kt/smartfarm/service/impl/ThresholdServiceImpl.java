/*
 * Copyright 2008-2009 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kt.smartfarm.service.impl;

import com.kt.smartfarm.service.ThresholdService;
import com.kt.smartfarm.service.ThresholdVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;

import com.kt.smartfarm.supervisor.mapper.ThresholdMapper;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;


@Service("thresholdService")
public class ThresholdServiceImpl extends EgovAbstractServiceImpl implements ThresholdService {
    private static final Logger log = LoggerFactory.getLogger(ThresholdServiceImpl.class);

    @Resource(name="thresholdMapper")
    private ThresholdMapper	thresholdMapper;

    @Autowired
    private DataSourceTransactionManager transactionManager;

	@Override
	public List<ThresholdVO> getThreshold(Integer gsmKey, Integer greenHouseId) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("gsmKey", gsmKey);
		param.put("greenHouseId", greenHouseId);
		return thresholdMapper.getThreshold(param);
	}

	@Override
	public int update(Integer gsmKey, Integer greenHouseId, List<ThresholdVO> thresholdVOs) {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

		TransactionStatus status =  transactionManager.getTransaction(def);
		int cnt = 0;
		try {
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("gsmKey", gsmKey);
			param.put("greenHouseId", greenHouseId);
			thresholdMapper.delete(param);

			for ( ThresholdVO thresholdVO : thresholdVOs ) {
				cnt += thresholdMapper.insert(thresholdVO);
			}
		} catch ( Exception ex ) {
			transactionManager.rollback(status);
			throw ex;
		}
		transactionManager.commit(status);
		return cnt;
	}
	public Integer copyToNewGSM(Integer fromGsmKey, Integer toGsmKey) {
		return thresholdMapper.copyToNewGSM(fromGsmKey, toGsmKey);
	}


}
