/*
 * Copyright 2011 MOPAS(Ministry of Public Administration and Security).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kt.smartfarm.supervisor.mapper;

import egovframework.customize.service.ThresholdVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * monitoring에 관한 데이터처리 매퍼 클래스
 *
 * @author  Sean
 * @since 2018.07.23
 * @version 1.0
 * @see <pre>
 *  == 개정이력(Modification Information) ==
 *
 *  수정일          			수정자           	수정내용
 *  -----------    		-----   	---------------------------
 *  2018.07.23        	Sean        최초 생성
 *
 * </pre>
 */
@Mapper("thresholdMapper")
public interface ThresholdMapper {
	Integer insert(ThresholdVO thresholdVO);
	Integer update(ThresholdVO thresholdVO);
	ThresholdVO getThreshold(String gsmKey);
}
