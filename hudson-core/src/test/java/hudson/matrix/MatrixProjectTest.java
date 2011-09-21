/*
 * The MIT License
 *
 * Copyright (c) 2011, Oracle Corporation, Anton Kozak
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package hudson.matrix;

import hudson.model.Result;
import java.io.IOException;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for {@link hudson.matrix.MatrixProject}
 */
public class MatrixProjectTest {

    @Test
    public void testIsRunSequentiallyParentTrue() throws IOException {
        MatrixProject parentProject = new MatrixProjectMock("parent");
        parentProject.setAllowSave(false);
        parentProject.setRunSequentially(true);

        MatrixProject childProject1 = new MatrixProject("child1");
        childProject1.setCascadingProject(parentProject);
        childProject1.setAllowSave(false);
        assertTrue(childProject1.isRunSequentially());
    }

    @Test
    public void testIsRunSequentiallyParentFalse() throws IOException {
        MatrixProject parentProject = new MatrixProjectMock("parent");
        parentProject.setAllowSave(false);
        parentProject.setRunSequentially(false);

        MatrixProject childProject1 = new MatrixProject("child1");
        childProject1.setCascadingProject(parentProject);
        childProject1.setAllowSave(false);
        assertFalse(childProject1.isRunSequentially());
    }

    @Test
    public void testIsRunSequentiallyDefaultValue() throws IOException {
        MatrixProject childProject1 = new MatrixProject("child1");
        childProject1.setAllowSave(false);
        assertFalse(childProject1.isRunSequentially());
    }

    @Test
    public void testIsRunSequentiallyParentFalseChildTrue() throws IOException {
        MatrixProject parentProject = new MatrixProjectMock("parent");
        parentProject.setAllowSave(false);
        parentProject.setRunSequentially(false);

        MatrixProject childProject1 = new MatrixProject("child1");
        childProject1.setAllowSave(false);
        childProject1.setCascadingProject(parentProject);
        childProject1.setRunSequentially(Boolean.TRUE);
        assertTrue(childProject1.isRunSequentially());
    }

    @Test
    public void testIsRunSequentiallyParentTrueChildFalse() throws IOException {
        MatrixProject parentProject = new MatrixProjectMock("parent");
        parentProject.setAllowSave(false);
        parentProject.setRunSequentially(Boolean.TRUE);

        MatrixProject childProject1 = new MatrixProject("child1");
        childProject1.setAllowSave(false);
        childProject1.setCascadingProject(parentProject);
        childProject1.setRunSequentially(false);
        assertFalse(childProject1.isRunSequentially());
    }

    @Test
    public void testSetRunSequentially() throws IOException {
        MatrixProject childProject1 = new MatrixProject("child1");
        childProject1.setAllowSave(false);
        childProject1.setRunSequentially(true);
        assertTrue(childProject1.isRunSequentially());
    }

    @Test
    public void testGetCombinationFilterChildValue() throws IOException {
        String parentCombinationFilter = "parent_filter";
        String childCombinationFilter = "child_filter";
        MatrixProject parentProject = new MatrixProjectMock("parent");
        parentProject.setAllowSave(false);
        parentProject.setCombinationFilter(parentCombinationFilter);

        MatrixProject childProject1 = new MatrixProject("child1"){
            @Override
            void rebuildConfigurations() throws IOException {
            }
        };
        childProject1.setAllowSave(false);
        childProject1.setCascadingProject(parentProject);
        childProject1.setCombinationFilter(childCombinationFilter);
        assertEquals(childCombinationFilter, childProject1.getCombinationFilter());
    }

    @Test
    public void testGetCombinationFilterParentValue() throws IOException {
        String parentCombinationFilter = "parent_filter";
        MatrixProject parentProject = new MatrixProjectMock("parent");
        parentProject.setAllowSave(false);
        parentProject.setCombinationFilter(parentCombinationFilter);

        MatrixProject childProject1 = new MatrixProject("child1"){
            @Override
            void rebuildConfigurations() throws IOException {
            }
        };
        childProject1.setAllowSave(false);
        childProject1.setCascadingProject(parentProject);
        assertEquals(childProject1.getCombinationFilter(), parentCombinationFilter);
    }

    @Test
    public void testSetCombinationFilterDifferentValues() throws IOException {
        String parentCombinationFilter = "parent_filter";
        String childCombinationFilter = "child_filter";
        MatrixProject parentProject = new MatrixProjectMock("parent");
        parentProject.setAllowSave(false);
        parentProject.setCombinationFilter(parentCombinationFilter);

        MatrixProject childProject1 = new MatrixProject("child1"){
            @Override
            void rebuildConfigurations() throws IOException {
            }
        };
        childProject1.setAllowSave(false);
        childProject1.setCascadingProject(parentProject);
        childProject1.setCombinationFilter(childCombinationFilter);
        assertEquals(childProject1.getCombinationFilter(), childCombinationFilter);
    }

    @Test
    public void testSetCombinationFilterTheSameValues() throws IOException {
        String combinationFilter = "filter";
        MatrixProject parentProject = new MatrixProjectMock("parent");
        parentProject.setAllowSave(false);
        parentProject.setCombinationFilter(combinationFilter);

        MatrixProject childProject1 = new MatrixProject("child1"){
            @Override
            void rebuildConfigurations() throws IOException {
            }
        };
        childProject1.setAllowSave(false);
        childProject1.setCascadingProject(parentProject);
        childProject1.setCombinationFilter(combinationFilter);
        assertEquals(childProject1.getCombinationFilter(), combinationFilter);
    }

    @Test
    public void testSetCombinationFilterParentNull() throws IOException {
        String combinationFilter = "filter";

        MatrixProject childProject1 = new MatrixProject("child1"){
            @Override
            void rebuildConfigurations() throws IOException {
            }
        };
        childProject1.setAllowSave(false);
        childProject1.setCombinationFilter(combinationFilter);
        assertEquals(childProject1.getCombinationFilter(), combinationFilter);
    }

    @Test
    public void testSetCombinationFilterNull() throws IOException {
        MatrixProject childProject1 = new MatrixProject("child1"){
            @Override
            void rebuildConfigurations() throws IOException {
            }
        };
        childProject1.setAllowSave(false);
        assertNull(childProject1.getCombinationFilter());
    }


    @Test
    public void testGetTouchStoneCombinationFilterChildValue() throws IOException {
        String parentCombinationFilter = "parent_filter";
        String childCombinationFilter = "child_filter";
        MatrixProject parentProject = new MatrixProjectMock("parent");
        parentProject.setAllowSave(false);
        parentProject.setTouchStoneCombinationFilter(parentCombinationFilter);

        MatrixProject childProject1 = new MatrixProject("child1"){
            @Override
            void rebuildConfigurations() throws IOException {
            }
        };
        childProject1.setAllowSave(false);
        childProject1.setCascadingProject(parentProject);
        childProject1.setTouchStoneCombinationFilter(childCombinationFilter);
        assertEquals(childProject1.getTouchStoneCombinationFilter(), childCombinationFilter);
    }

    @Test
    public void testGetTouchStoneCombinationFilterParentValue() throws IOException {
        String parentCombinationFilter = "parent_filter";
        MatrixProject parentProject = new MatrixProjectMock("parent");
        parentProject.setAllowSave(false);
        parentProject.setTouchStoneCombinationFilter(parentCombinationFilter);

        MatrixProject childProject1 = new MatrixProject("child1"){
            @Override
            void rebuildConfigurations() throws IOException {
            }
        };
        childProject1.setAllowSave(false);
        childProject1.setCascadingProject(parentProject);
        assertEquals(childProject1.getTouchStoneCombinationFilter(), parentCombinationFilter);
    }

    @Test
    public void testGetTouchStoneCombinationNull() throws IOException {
        MatrixProject childProject1 = new MatrixProject("child1"){
            @Override
            void rebuildConfigurations() throws IOException {
            }
        };
        childProject1.setAllowSave(false);
        assertNull(childProject1.getTouchStoneCombinationFilter());
    }

    @Test
    public void testSetTouchStoneCombinationFilterDifferentValues() throws IOException {
        String parentCombinationFilter = "parent_filter";
        String childCombinationFilter = "child_filter";
        MatrixProject parentProject = new MatrixProjectMock("parent");
        parentProject.setAllowSave(false);
        parentProject.setTouchStoneCombinationFilter(parentCombinationFilter);

        MatrixProject childProject1 = new MatrixProject("child1"){
            @Override
            void rebuildConfigurations() throws IOException {
            }
        };
        childProject1.setAllowSave(false);
        childProject1.setCascadingProject(parentProject);
        childProject1.setTouchStoneCombinationFilter(childCombinationFilter);
        assertEquals(childProject1.getTouchStoneCombinationFilter(), childCombinationFilter);
    }

    @Test
    public void testSetTouchStoneCombinationFilterTheSameValues() throws IOException {
        String combinationFilter = "filter";
        MatrixProject parentProject = new MatrixProjectMock("parent");
        parentProject.setAllowSave(false);
        parentProject.setTouchStoneCombinationFilter(combinationFilter);

        MatrixProject childProject1 = new MatrixProject("child1"){
            @Override
            void rebuildConfigurations() throws IOException {
            }
        };
        childProject1.setAllowSave(false);
        childProject1.setCascadingProject(parentProject);
        childProject1.setTouchStoneCombinationFilter(combinationFilter);
        assertEquals(childProject1.getTouchStoneCombinationFilter(), combinationFilter);
    }

    @Test
    public void testSetTouchStoneCombinationFilterParentNull() throws IOException {
        String combinationFilter = "filter";

        MatrixProject childProject1 = new MatrixProject("child1"){
            @Override
            void rebuildConfigurations() throws IOException {
            }
        };
        childProject1.setAllowSave(false);
        childProject1.setTouchStoneCombinationFilter(combinationFilter);
        assertEquals(childProject1.getTouchStoneCombinationFilter(), combinationFilter);
    }

    @Test
    public void testGetTouchStoneResultConditionChildValue() throws IOException {
        Result parentResultCondition = Result.SUCCESS;
        Result childResultCondition = Result.FAILURE;
        MatrixProject parentProject = new MatrixProjectMock("parent");
        parentProject.setAllowSave(false);
        parentProject.setTouchStoneResultCondition(parentResultCondition);

        MatrixProject childProject1 = new MatrixProject("child1"){
            @Override
            void rebuildConfigurations() throws IOException {
            }
        };
        childProject1.setAllowSave(false);
        childProject1.setCascadingProject(parentProject);
        childProject1.setTouchStoneResultCondition(childResultCondition);
        assertEquals(childProject1.getTouchStoneResultCondition(), childResultCondition);
    }

    @Test
    public void testGetTouchStoneResultConditionParentValue() throws IOException {
        Result parentResultCondition = Result.SUCCESS;
        MatrixProject parentProject = new MatrixProjectMock("parent");
        parentProject.setAllowSave(false);
        parentProject.setTouchStoneResultCondition(parentResultCondition);

        MatrixProject childProject1 = new MatrixProject("child1"){
            @Override
            void rebuildConfigurations() throws IOException {
            }
        };
        childProject1.setAllowSave(false);
        childProject1.setCascadingProject(parentProject);
        assertEquals(childProject1.getTouchStoneResultCondition(), parentResultCondition);
    }

    @Test
    public void testSetTouchStoneResultConditionDifferentValues() throws IOException {
        Result parentResultCondition = Result.SUCCESS;
        Result childResultCondition = Result.FAILURE;
        MatrixProject parentProject = new MatrixProjectMock("parent");
        parentProject.setAllowSave(false);
        parentProject.setTouchStoneResultCondition(parentResultCondition);

        MatrixProject childProject1 = new MatrixProject("child1"){
            @Override
            void rebuildConfigurations() throws IOException {
            }
        };
        childProject1.setAllowSave(false);
        childProject1.setCascadingProject(parentProject);
        childProject1.setTouchStoneResultCondition(childResultCondition);
        assertEquals(childProject1.getTouchStoneResultCondition(), childResultCondition);
    }

    @Test
    public void testSetTouchStoneResultConditionTheSameValues() throws IOException {
        Result parentResultCondition = Result.SUCCESS;
        MatrixProject parentProject = new MatrixProjectMock("parent");
        parentProject.setAllowSave(false);
        parentProject.setTouchStoneResultCondition(parentResultCondition);

        MatrixProject childProject1 = new MatrixProject("child1"){
            @Override
            void rebuildConfigurations() throws IOException {
            }
        };
        childProject1.setAllowSave(false);
        childProject1.setCascadingProject(parentProject);
        childProject1.setTouchStoneResultCondition(parentResultCondition);
        assertEquals(childProject1.getTouchStoneResultCondition(), parentResultCondition);
    }

    @Test
    public void testSetTouchStoneResultConditionParentNull() throws IOException {
        Result childResultCondition = Result.FAILURE;

        MatrixProject childProject1 = new MatrixProject("child1"){
            @Override
            void rebuildConfigurations() throws IOException {
            }
        };
        childProject1.setAllowSave(false);
        childProject1.setTouchStoneResultCondition(childResultCondition);
        assertEquals(childProject1.getTouchStoneResultCondition(), childResultCondition);
    }

    @Test
    public void testSetTouchStoneResultConditionNull() throws IOException {

        MatrixProject childProject1 = new MatrixProject("child1"){
            @Override
            void rebuildConfigurations() throws IOException {
            }
        };
        childProject1.setAllowSave(false);
        assertNull(childProject1.getTouchStoneResultCondition());
    }

    @Test
    public void testGetCustomWorkspaceChildValue() throws IOException {
        String parentWorkspace = "/tmp";
        String childWorkspace = "/tmp2";
        MatrixProject parentProject = new MatrixProjectMock("parent");
        parentProject.setAllowSave(false);
        parentProject.setCustomWorkspace(parentWorkspace);

        MatrixProject childProject1 = new MatrixProject("child1"){
            @Override
            void rebuildConfigurations() throws IOException {
            }
        };
        childProject1.setAllowSave(false);
        childProject1.setCascadingProject(parentProject);
        childProject1.setCustomWorkspace(childWorkspace);
        assertEquals(childProject1.getCustomWorkspace(), childWorkspace);
    }

    @Test
    public void testGetCustomWorkspaceParentValue() throws IOException {
        String parentWorkspace = "/tmp";
        MatrixProject parentProject = new MatrixProjectMock("parent");
        parentProject.setAllowSave(false);
        parentProject.setCustomWorkspace(parentWorkspace);

        MatrixProject childProject1 = new MatrixProject("child1"){
            @Override
            void rebuildConfigurations() throws IOException {
            }
        };
        childProject1.setAllowSave(false);
        childProject1.setCascadingProject(parentProject);
        assertEquals(childProject1.getCustomWorkspace(), parentWorkspace);
    }

    @Test
    public void testGetCustomWorkspaceNull() throws IOException {
        MatrixProject childProject1 = new MatrixProject("child1"){
            @Override
            void rebuildConfigurations() throws IOException {
            }
        };
        childProject1.setAllowSave(false);
        assertNull(childProject1.getCustomWorkspace());
    }

    @Test
    public void testSetCustomWorkspaceDifferentValues() throws IOException {
        String parentWorkspace = "/tmp";
        String childWorkspace = "/tmp2";
        MatrixProject parentProject = new MatrixProjectMock("parent");
        parentProject.setAllowSave(false);
        parentProject.setTouchStoneCombinationFilter(parentWorkspace);

        MatrixProject childProject1 = new MatrixProject("child1"){
            @Override
            void rebuildConfigurations() throws IOException {
            }
        };
        childProject1.setAllowSave(false);
        childProject1.setCascadingProject(parentProject);
        childProject1.setCustomWorkspace(childWorkspace);
        assertEquals(childProject1.getCustomWorkspace(), childWorkspace);
    }

    @Test
    public void testSetCustomWorkspaceTheSameValues() throws IOException {
        String parentWorkspace = "/tmp";
        MatrixProject parentProject = new MatrixProjectMock("parent");
        parentProject.setAllowSave(false);
        parentProject.setCustomWorkspace(parentWorkspace);

        MatrixProject childProject1 = new MatrixProject("child1"){
            @Override
            void rebuildConfigurations() throws IOException {
            }
        };
        childProject1.setAllowSave(false);
        childProject1.setCascadingProject(parentProject);
        childProject1.setCustomWorkspace(parentWorkspace);
        assertEquals(childProject1.getCustomWorkspace(), parentWorkspace);
    }

    @Test
    public void testSetCustomWorkspaceParentNull() throws IOException {
        String parentWorkspace = "/tmp";

        MatrixProject childProject1 = new MatrixProject("child1"){
            @Override
            void rebuildConfigurations() throws IOException {
            }
        };
        childProject1.setAllowSave(false);
        childProject1.setCustomWorkspace(parentWorkspace);
        assertEquals(childProject1.getCustomWorkspace(), parentWorkspace);
    }

    private class MatrixProjectMock extends MatrixProject {

        private MatrixProjectMock(String name) {
            super(null, name);
        }

        @Override
        protected void updateTransientActions() {
        }

        @Override
        void rebuildConfigurations() throws IOException {
        }
    }
}
