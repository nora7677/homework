package cn.com.taiji.helloJPAM.pojo;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "emps")
public class Emp {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column
	private String name;

	@JoinTable(name = "emp_job", joinColumns = { @JoinColumn(name = "emp_id", referencedColumnName = "id") }, inverseJoinColumns = {
			@JoinColumn(name = "job_id", referencedColumnName = "id") })
	@ManyToMany(cascade = { CascadeType.REMOVE })
	private List<Job> jobs;

	@Override
	public String toString() {
		return "Emp [id=" + id + ", name=" + name + ", jobs=" + jobs + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Job> getJobs() {
		return jobs;
	}

	public void setJobs(List<Job> jobs) {
		this.jobs = jobs;
	}

}
