package com.duowan.flowengine.model;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.duowan.flowengine.model.def.FlowDef;
import com.duowan.flowengine.util.Listener;
import com.duowan.flowengine.util.Listenerable;

/**
 * 流程实例,一个流程包括多个任务(FlowTask)
 * 
 * @author badqiu
 *
 */
public class Flow extends FlowDef<FlowTask>{
	private String instanceId; //实例ID
	private String status; //任务状态: 可运行,运行中,阻塞(睡眠,等待),停止
	private int execResult = 0; //执行结果: 0成功,非0为失败

	private Date startTime;
	private Date endTime;
	private StringBuffer log = new StringBuffer();
	
	private transient Listenerable<Flow> listenerable = new Listenerable<Flow>();
	
	private Map context = new HashMap();
	
	public Flow() {
	}
	
	public Flow(String flowCode,String instanceId) {
		super();
		this.instanceId = instanceId;
		setFlowCode(flowCode);
	}
	
	/**
	 * 初始化图
	 */
	public void init() {
		super.init();
		for(FlowTask flowTask : super.getNodes()) {
			Set<FlowTask> unFinishParents = new HashSet<FlowTask>(flowTask.getParents());
			flowTask.setUnFinishParents(unFinishParents);
		}
	}
	
	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getExecResult() {
		return execResult;
	}

	public void setExecResult(int execResult) {
		this.execResult = execResult;
	}
	

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public StringBuffer getLog() {
		return log;
	}

	public void setLog(StringBuffer log) {
		this.log = log;
	}
	
	public void addLog(String txt) {
		this.log.append(txt);
	}

	public void notifyListeners() {
		listenerable.notifyListeners(this, null);
	}

	public void addListener(Listener<Flow> t) {
		listenerable.addListener(t);
	}

	/**
	 * @return the context
	 */
	public Map getContext() {
		return context;
	}

	/**
	 * @param context the context to set
	 */
	public void setContext(Map context) {
		this.context = context;
	}
}
