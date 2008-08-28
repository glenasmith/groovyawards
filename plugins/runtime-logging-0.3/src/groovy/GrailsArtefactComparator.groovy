class GrailsArtefactComparator implements Comparator {

    public int compare(Object artefact1, Object artefact2) {

        String name1 = artefact1.logicalPropertyName
        String name2 = artefact2.logicalPropertyName

        return name1.compareTo(name2)
    }
}
