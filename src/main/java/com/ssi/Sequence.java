package com.ssi;

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
    return String.format("Sequence{seqGroup=%d, batch=%d}", seqGroup, batch);
  }
  
}
