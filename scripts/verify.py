"""Compile each independent tutorial and check its observable behavior.

Usage: python scripts/verify.py [--java-home PATH]
Uses only the Python standard library. Java source requires JDK 8 or later.
"""
import argparse
import json
import os
from pathlib import Path
import shutil
import subprocess
import tempfile

ROOT = Path(__file__).resolve().parents[1]
BUILD = ROOT / "build"


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("--java-home", default=os.environ.get("JAVA_HOME"))
    args = parser.parse_args()
    suffix = ".exe" if os.name == "nt" else ""
    javac = str(Path(args.java_home) / "bin" / ("javac" + suffix)) if args.java_home else shutil.which("javac")
    java = str(Path(args.java_home) / "bin" / ("java" + suffix)) if args.java_home else shutil.which("java")
    if not javac or not java:
        parser.error("JDK not found: set JAVA_HOME or supply --java-home.")
    BUILD.mkdir(exist_ok=True)
    results = []

    def command(argv, *, cwd=ROOT, input_text=None, expected_code=0):
        result = subprocess.run([str(x) for x in argv], cwd=cwd,
                                input=None if input_text is None else input_text.encode("utf-8"),
                                stdout=subprocess.PIPE, stderr=subprocess.PIPE, timeout=30)
        out = result.stdout.decode("utf-8", errors="replace").replace("\r\n", "\n")
        err = result.stderr.decode("utf-8", errors="replace")
        if result.returncode != expected_code:
            raise AssertionError(f"Command failed ({result.returncode}): {argv}\n{out}\n{err}")
        return out, err

    def compile_group(name, folder, tests=()):
        target = BUILD / name
        target.mkdir(exist_ok=True)
        sources = sorted((ROOT / folder).glob("*.java")) + [ROOT / "tests" / test for test in tests]
        out, err = command([javac, "-encoding", "UTF-8", "-Xlint:all", "-d", target, *sources])
        if err.strip():
            raise AssertionError("Compiler warnings or errors: " + err)
        return target

    def run(target, entry, *argv, input_text=None, cwd=ROOT, expected_code=0):
        return command([java, "-Dfile.encoding=UTF-8", "-cp", target, entry, *argv],
                       input_text=input_text, cwd=cwd, expected_code=expected_code)

    def record(name, note, output=""):
        results.append({"name": name, "status": "PASS", "details": note})
        if output:
            (BUILD / (name + "_output.txt")).write_text(output, encoding="utf-8")
        print("PASS", name, "-", note)

    target = compile_group("tutorial_1", "tutorial_1", ["GradeTest.java"])
    assert run(target, "HelloWorld")[0] == "Hello World\n"
    assert run(target, "tutorial_1")[0] == "Hello World\n"
    output = run(target, "StudentGrade")[0]
    assert "Grade: A" in output
    run(target, "GradeTest")
    run(target, "StudentGrade", "bad", expected_code=1)
    record("tutorial_1", "HelloWorld, original starter, example grade and grade boundaries", output)

    target = compile_group("week_2", "tutorial_1/week_2")
    output = run(target, "Main")[0]
    assert output == "Name: Ali\nAge: 20\nGPA: 3.75\nAli is studying.\nAli is taking an exam.\n"
    record("week_2", "Student constructor and three method calls", output)

    target = compile_group("tutorial_2", "tutorial_2")
    output = run(target, "Main")[0]
    assert output == "I am a person.\nI am a student.\nI am a lecturer.\n"
    record("tutorial_2", "Person references dispatch to all three implementations", output)

    target = compile_group("classroom_car", "tutorial_2/classroom_car")
    output = run(target, "Main")[0]
    assert output == "Brand: toyota\nColor: grey\nSpeed: 100\n"
    record("classroom_car", "Photographed Car fields and displayInfo", output)

    target = compile_group("tutorial_3", "tutorial_3", ["EncapsulationTest.java"])
    output = run(target, "Main")[0]
    assert "CU12345" in output and "3.75" in output and "Programme  : BIT" in output
    run(target, "EncapsulationTest")
    record("tutorial_3", "Private fields, getters/setters, CGPA boundaries and rejected input", output)

    target = compile_group("tutorial_4", "tutorial_4")
    output = run(target, "Main")[0]
    assert output == ("Employee ID : L100\nName        : Dr Ahmad\nSubject     : Java Programming\n"
                      "Department  : Faculty of Information Technology\n")
    record("tutorial_4", "Inherited employee information, subject and added department", output)

    target = compile_group("tutorial_5", "tutorial_5")
    output = run(target, "Main")[0]
    for status in ("Washing clothes...", "Store food & beverages...", "Cooling the room...", "Heating food..."):
        assert status in output
    assert output.count("Power ON") == 4 and output.count("Power OFF") == 4
    record("tutorial_5", "Abstract Appliance dispatches to four appliance types", output)

    target = compile_group("tutorial_6", "tutorial_6")
    with tempfile.TemporaryDirectory(prefix="task_checks_", dir=BUILD) as temporary:
        cwd = Path(temporary)
        tasks = cwd / "task.txt"
        output = run(target, "Main", input_text="drink water\nreview Java\nread notes\n", cwd=cwd)[0]
        assert tasks.read_text(encoding="utf-8").splitlines() == ["drink water", "review Java", "read notes"]
        second = run(target, "Main", input_text="\nwalk\npractice\n复习Java\n", cwd=cwd)[0]
        saved = tasks.read_bytes()
        assert "PREVIOUS TASKS" in second and "Task cannot be blank" in second
        assert tasks.read_text(encoding="utf-8").splitlines() == ["drink water", "review Java", "read notes", "walk", "practice", "复习Java"]
        early = run(target, "Main", input_text="unfinished\n", cwd=cwd)[0]
        assert "No changes were saved" in early and tasks.read_bytes() == saved
        run(target, "Main", str(cwd), input_text="a\nb\nc\n", cwd=cwd, expected_code=1)
        assert tasks.read_bytes() == saved
        run(target, "Main", str(cwd / "missing" / "task.txt"), input_text="a\nb\nc\n", cwd=cwd, expected_code=1)
        assert not list(cwd.glob("tasks-*.tmp"))
        record("tutorial_6", "First save, automatic reload, append, blank retry, UTF-8, EOF and I/O errors", output + "\nSECOND RUN\n" + second)

    target = compile_group("tutorial_7", "tutorial_7", ["QuizGuiTest.java"])
    output = run(target, "QuizGuiTest", str(BUILD / "gui"))[0]
    record("tutorial_7", "Swing button events, retry, score once, all questions, completion and restart", output)

    target = compile_group("existing_payroll", ".")
    output = run(target, "PayrollSystem", input_text="0\n")[0]
    assert "Goodbye." in output
    record("existing_payroll", "Existing Assignment 2 code remains unchanged and starts/exits", output)

    version = command([javac, "-version"])
    report = {"compiler": (version[0] + version[1]).strip(), "checks": results, "status": "PASS"}
    (BUILD / "verification.json").write_text(json.dumps(report, indent=2), encoding="utf-8")
    print(f"All {len(results)} verification groups passed.")


if __name__ == "__main__":
    main()
