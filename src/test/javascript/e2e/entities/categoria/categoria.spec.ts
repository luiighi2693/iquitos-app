/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CategoriaComponentsPage, CategoriaDeleteDialog, CategoriaUpdatePage } from './categoria.page-object';

const expect = chai.expect;

describe('Categoria e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let categoriaUpdatePage: CategoriaUpdatePage;
    let categoriaComponentsPage: CategoriaComponentsPage;
    let categoriaDeleteDialog: CategoriaDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Categorias', async () => {
        await navBarPage.goToEntity('categoria');
        categoriaComponentsPage = new CategoriaComponentsPage();
        expect(await categoriaComponentsPage.getTitle()).to.eq('Categorias');
    });

    it('should load create Categoria page', async () => {
        await categoriaComponentsPage.clickOnCreateButton();
        categoriaUpdatePage = new CategoriaUpdatePage();
        expect(await categoriaUpdatePage.getPageTitle()).to.eq('Create or edit a Categoria');
        await categoriaUpdatePage.cancel();
    });

    it('should create and save Categorias', async () => {
        const nbButtonsBeforeCreate = await categoriaComponentsPage.countDeleteButtons();

        await categoriaComponentsPage.clickOnCreateButton();
        await promise.all([categoriaUpdatePage.setNombreInput('nombre'), categoriaUpdatePage.setNumeroProductosInput('5')]);
        expect(await categoriaUpdatePage.getNombreInput()).to.eq('nombre');
        expect(await categoriaUpdatePage.getNumeroProductosInput()).to.eq('5');
        await categoriaUpdatePage.save();
        expect(await categoriaUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await categoriaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Categoria', async () => {
        const nbButtonsBeforeDelete = await categoriaComponentsPage.countDeleteButtons();
        await categoriaComponentsPage.clickOnLastDeleteButton();

        categoriaDeleteDialog = new CategoriaDeleteDialog();
        expect(await categoriaDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Categoria?');
        await categoriaDeleteDialog.clickOnConfirmButton();

        expect(await categoriaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
