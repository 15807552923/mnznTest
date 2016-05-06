package com.mnzn.ecode.repository;

import java.util.List;
import java.util.Map;

import com.mnzn.ecode.domain.BoxWashCount;

public interface BoxWashDaoPlus {

	public List<BoxWashCount> getBoxWashList(Map map);
	List<BoxWashCount> getBoxWashDetailList(Map map);
}
