#!/usr/bin/env -S ruby -w
projectdir = Dir.pwd
gitdir     = "#{projectdir}/.git"
root       = ENV["TESTPATH"]
outdir     = "expected"
all        = "dummy empty tabbed three"

# Enter test directory
if !root.nil?
  Dir.chdir root
else
  STDERR.puts "Not in tests directory."
  Kernel.exit 1
end

if not File.directory? outdir
  `mkdir -p #{outdir}`
end 

# generate a large file for consistency check
consistent = "consistent/consistent.txt"

if ENV['NO_CONST'] == "y"
  puts "[INFO] NO_CONST=y: not generating consistency expects"
else
  # backup the template
  tmp = "#{consistent}.tmp"
  if File.size?(consistent) < 1024 
    File.copy_stream(consistent, tmp)
    size = 10 * 1024 * 1024
    while File.size?(consistent) <= size do
      `find #{gitdir} -type f -exec cat {} >> #{consistent} ';'`
    end
    `echo "[INFO] Poking hornet nest here." >> #{consistent}`
  end
  all += " consistent "
end


# so that "thebustle" corresponds to "thebustle/thebustle.txt" and "expected/thebustle.out"
for basename in all.split(" ")
  file = "#{basename}.txt"
  # using the files directly
  `cat    #{basename}/#{file} > #{outdir}/#{basename}.out`
  `cat -b #{basename}/#{file} > #{outdir}/#{basename}_b.out`
  `cat -n #{basename}/#{file} > #{outdir}/#{basename}_n.out`
  `cat -s #{basename}/#{file} > #{outdir}/#{basename}_s.out`
  `cat -v #{basename}/#{file} > #{outdir}/#{basename}_v.out`
  `cat -E #{basename}/#{file} > #{outdir}/#{basename}_E.out`
  `cat -T #{basename}/#{file} > #{outdir}/#{basename}_T.out`

  # passing the content of the file to cat's stdin
  `cat    < #{basename}/#{file} > #{outdir}/#{basename}_stdin.out`
  `cat -b < #{basename}/#{file} > #{outdir}/#{basename}_stdin_b.out`
  `cat -n < #{basename}/#{file} > #{outdir}/#{basename}_stdin_n.out`
  `cat -s #{basename}/#{file} > #{outdir}/#{basename}_stdin_s.out`
  `cat -v #{basename}/#{file} > #{outdir}/#{basename}_stdin_v.out`
  `cat -E #{basename}/#{file} > #{outdir}/#{basename}_stdin_E.out`
  `cat -T #{basename}/#{file} > #{outdir}/#{basename}_stdin_T.out`
end

# exceptionals
multiple = ["dummy/dummy.txt", "empty/empty.txt", "tabbed/tabbed.txt", "three/three.txt"].join(" ")

`cat -b   #{multiple} > expected/multiple_b.out`
`cat -v   #{multiple} > expected/multiple_v.out`

`cat #{multiple} | cat -b > expected/multiple_stdin_b.out`
`cat #{multiple} | cat -v > expected/multiple_stdin_v.out`
`cat #{multiple} | cat -T > expected/multiple_stdin_T.out`

# transport style
# `cat -b -E    #{bustle} > #{outdir}/the-bustle_bE.out`
# `cat -n -E    #{bustle} > #{outdir}/the-bustle_nE.out`
# `cat -E    <  #{bustle} > #{outdir}/the-bustle_stdin_E.out`
# `cat -b -E <  #{bustle} > #{outdir}/the-bustle_stdin_bE.out`
# `cat -n -E <  #{bustle} > #{outdir}/the-bustle_stdin_nE.out`

# `cat           #{three} >  #{outdir}/three.out`
# `cat -n        #{three} >  #{outdir}/three_n.out`
# `cat -E        #{three} >  #{outdir}/three_E.out`
# `cat -n -E     #{three} >  #{outdir}/three_nE.out`
# `cat       <   #{three} >  #{outdir}/three_stdin.out`
# `cat -n    <   #{three} >  #{outdir}/three_stdin_n.out`
# `cat -E    <   #{three} >  #{outdir}/three_stdin_E.out`
# `cat -n -E <   #{three} >  #{outdir}/three_stdin_nE.out`

# `cat       #{all} > #{outdir}/all.out`
# `cat -b    #{all} > #{outdir}/all_b.out`
# `cat -b -E #{all} > #{outdir}/all_bE.out`
# `cat -n    #{all} > #{outdir}/all_n.out`
# `cat -n -E #{all} > #{outdir}/all_nE.out`

Dir.chdir projectdir
exit 0
