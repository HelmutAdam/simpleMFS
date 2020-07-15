package com.ssi.application_project;

public class Sequence {
  private final long seqGroup;
  private final long batch;
  
  public Sequence(long seqGroup, long batch) {
    this.seqGroup = seqGroup;
    this.batch = batch;
  }

  public long getSeqGroup() {
    return seqGroup;
  }

  public long getBatch() {
    return batch;
  }

  @Override
  public String toString() {
    return "Sequence seqGroup=" + seqGroup + ", batch=" + batch;
  }
  
}
